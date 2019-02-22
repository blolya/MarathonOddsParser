package marathon.objects.categories

import com.beust.klaxon.Klaxon

class Category(private var trees: MutableList<Int>) {

    constructor(categoryResponse: String): this(mutableListOf()) {
        val category = Klaxon().parse<Children>(categoryResponse)
        if (category != null) {
            setInnermostTreesIds(category)
        }
    }

    fun getTrees(): List<Int> {
        return this.trees
    }

    private fun setInnermostTreesIds(category: Children) {
        if (category.catInfo.children.size != 0) {
            category.catInfo.children.forEach { children -> run {
                setInnermostTreesIds(children)
            } }
        } else {
            this.trees.add(category.treeId)
        }
    }
}