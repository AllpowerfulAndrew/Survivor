package model.db.entity

import javax.persistence.*

import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "descriptions")
class Description() {

    constructor(key: String?, value: String?, itemId: Item?) : this() {
        this.key = key
        this.value = value
        this.itemId = itemId
    }

    constructor(key: String?, value: String?, sectionId: Section?) : this() {
        this.key = key
        this.value = value
        this.sectionId = sectionId
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "key")
    var key: String? = null

    @Column(name = "value")
    var value: String? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "item_id")
    var itemId: Item? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "section_id")
    var sectionId: Section? = null

    override fun toString() = "Description(key=$key, value=$value)"
}
