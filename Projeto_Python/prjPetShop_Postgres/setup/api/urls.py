from django.urls import path

from .views import (
    get_donos,
    create_dono,
    dono_detail,
    get_pets,
    create_pet,
    pet_detail
)

urlpatterns = [
    # URLs para Donos
    path('donos/', get_donos, name='get_donos'),
    path('donos/create/', create_dono, name='create_dono'),
    path('donos/<int:pk>/', dono_detail, name='dono_detail'),

    # URLs para Pets
    path('pets/', get_pets, name='get_pets'),
    path('pets/create/', create_pet, name='create_pet'),
    path('pets/<int:pk>/', pet_detail, name='pet_detail'),
]