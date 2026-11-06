from app.database import mongo

class Dono:
    @staticmethod
    def to_dict(dono):
        # Se o _id não existir ou estiver vazio
        if dono.get("_id") is None or str(dono.get("_id")) == "":
            return {
                "nome": dono["nome"],
                "data_nascimento": dono.get("data_nascimento", ""),
                "cpf": dono["cpf"],
                "pets": dono["pets"],
            }
        else:
            return {
                "id": str(dono["_id"]),
                "nome": dono["nome"],
                "data_nascimento": dono.get("data_nascimento", ""),
                "cpf": dono["cpf"],
                "pets": dono["pets"],
            }