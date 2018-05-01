package db.entity


import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "items")
class Item {

    @Id
    @Column(name = "id")
    var id: String? = null

    @Column(name = "name")
    var name: String? = null

    @OneToMany(fetch = EAGER, mappedBy = "item")
    var descriptions: Set<Description>? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "location_id")
    var location: Location? = null
}
