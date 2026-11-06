from flask import Blueprint, request, jsonify
from bson.objectid import ObjectId
from app.database import mongo
from app.models import Dono

api_bp = Blueprint("api", __name__)

@api_bp.route("/donos", methods=["POST"])  
def create_dono():
    data = request.json
    dono = Dono.to_dict(data)
    result = mongo.db.donos.insert_one(dono)
    return jsonify({"id": str(result.inserted_id), **dono}), 201

@api_bp.route("/donos", methods=["GET"])
def get_donos():
    donos = mongo.db.donos.find()
    return jsonify([Dono.to_dict(dono) for dono in donos])

@api_bp.route("/donos/<string:dono_id>", methods=["GET"])
def get_dono_by_id(dono_id):  
    dono = mongo.db.donos.find_one({"_id": ObjectId(dono_id)})
    if dono:
        return jsonify(Dono.to_dict(dono))
    return jsonify({"error": "Dono not found"}), 404

@api_bp.route("/donos/<string:dono_id>", methods=["PUT"]) 
def update_dono(dono_id):
    data = request.json
    update_data = {"$set": Dono.to_dict(data)}
    
    result = mongo.db.donos.update_one({"_id": ObjectId(dono_id)}, update_data)

    if result.matched_count:
        updated_dono = mongo.db.donos.find_one({"_id": ObjectId(dono_id)})
        return jsonify(Dono.to_dict(updated_dono))
    return jsonify({"error": "Dono not found"}), 404

@api_bp.route("/donos/<string:dono_id>", methods=["DELETE"])
def delete_dono(dono_id):
    result = mongo.db.donos.delete_one({"_id": ObjectId(dono_id)})
    if result.deleted_count:
        return jsonify({"message": "Dono Deleted successfully"})
    return jsonify({"error": "Dono not found"}), 404