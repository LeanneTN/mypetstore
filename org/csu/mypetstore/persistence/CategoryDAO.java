package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Category;

import java.util.List;

public interface CategoryDAO {
    //获取所有的大类
    List<Category> getCategoryList();

    //用id获取其中的一个类
    Category getCategory(String categoryId);
}
