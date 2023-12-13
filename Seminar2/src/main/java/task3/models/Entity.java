package task3.models;


import task3.Column;

import java.util.UUID;

@task3.Entity
public class Entity {
    // можно создать базовую сущность Entity с UUID
    // и наследоваться от нее в Employee (таблице), уже без id

    @Column(name = "id", primaryKey = true)
    private UUID id;

}
