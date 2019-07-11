package ir.rayapars.honarfakher.classes;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpenCategory extends ExpandableGroup<Category> {

    public ExpenCategory(Category category, List<Category> items) {
        super(category.getTitle(), items);
    }
}