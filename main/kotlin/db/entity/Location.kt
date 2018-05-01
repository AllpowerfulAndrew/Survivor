package db.entity

import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "locations")
class Location() {

    constructor(id: String?) : this() {
        this.id = id
    }

    @Id
    @Column(name = "id")
    var id: String? = null

    @OneToMany(fetch = EAGER, mappedBy = "location")
    var sections: Set<Section>? = null

    @OneToMany(fetch = EAGER, mappedBy = "location")
    var items: Set<Item>? = null
}