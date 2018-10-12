package model.db.entity

import javax.persistence.*

@Entity
@Table(name = "calls")
class Call() {

    constructor(value: String?, itemId: Item?) : this(){
        this.value = value
        this.itemId = itemId
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "value")
    var value: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    var itemId: Item? = null

    override fun toString() = "Call(value=$value)"
}