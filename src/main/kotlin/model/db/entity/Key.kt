package model.db.entity

import javax.persistence.*

@Entity
@Table(name = "keys")
class Key {

    @Id
    @Column(name = "key")
    var key: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    var itemId: Item? = null
}