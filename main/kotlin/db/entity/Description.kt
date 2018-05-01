package db.entity

import javax.persistence.*

import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "descriptions")
class Description() {

    constructor(id: String, value: String, item: Item) : this() {
        this.id = id
        this.value = value
        this.item = item
    }

    constructor(id: String, value: String, section: Section) : this() {
        this.id = id
        this.value = value
        this.section = section
    }

    @Id
    @Column(name = "id")
    var id: String? = null

    @Column(name = "value")
    var value: String? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "item_id")
    var item: Item? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "section_id")
    var section: Section? = null
}
