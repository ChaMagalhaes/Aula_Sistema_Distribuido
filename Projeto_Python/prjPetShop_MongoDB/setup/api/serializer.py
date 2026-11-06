from rest_framework import serializers
from .models import Dono, Pet

class PetSerializer(serializers.Serializer):
    nome = serializers.CharField(max_length=100)
    raca = serializers.CharField(max_length=100)
    especie = serializers.CharField(max_length=50)
    data_nascimento = serializers.DateTimeField()
    porte = serializers.CharField(max_length=100)
    cor = serializers.CharField(max_length=50)

class DonoSerializer(serializers.ModelSerializer):
    id = serializers.SerializerMethodField()
    pets = PetSerializer(allow_null=True)

    class Meta:
        model = Dono
        fields = ['id', 'nome', 'data_nascimento', 'cpf', 'pets']

    def get_id(self, obj):
        return str(obj.id) if obj.id else None

    def create(self, validated_data):
        pet_data = validated_data.pop('pets', None)
        dono = Dono(**validated_data)

        if pet_data:
            dono.pets = Pet(**pet_data)

        dono.save()
        return dono

    def update(self, instance, validated_data):
        pet_data = validated_data.pop('pets', None)

        for attr, value in validated_data.items():
            setattr(instance, attr, value)

        if pet_data:
            instance.pets = Pet(**pet_data)
        else:
            instance.pets = None

        instance.save()
        return instance
        