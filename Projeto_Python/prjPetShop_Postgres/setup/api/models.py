
from django.db import models


class Dono(models.Model):
    nome = models.CharField(max_length=100)
    data_nascimento = models.DateField()
    cpf = models.CharField(max_length=14, unique=True)

    def __str__(self):
        return f"{self.nome} ({self.cpf})"


class Pet(models.Model):

    PORTE_CHOICES = [
        ('pequeno', 'Pequeno'),
        ('medio', 'Médio'),
        ('grande', 'Grande'),
    ]

    nome = models.CharField(max_length=100)
    raca = models.CharField(max_length=100)
    especie = models.CharField(max_length=50)
    data_nascimento = models.DateField()
    porte = models.CharField(max_length=10, choices=PORTE_CHOICES)
    cor = models.CharField(max_length=50)

    dono = models.ForeignKey(
        Dono,
        on_delete=models.CASCADE,
        related_name='pets'
    )

    def __str__(self):
        return f"{self.nome} - {self.especie}"
