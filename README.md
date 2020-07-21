# blog
### 项目引入的技术和框架
- Web框架：SpringBoot
- 前端模板：Thymeleaf
- 数据库：MySQL
- 数据库ORM：JPA

  <hr>

#### 数据库名默认为**myblog**，其中有`article`表和`user`表；

> 页面的布局还需优化

##### 文章的添加、修改，以及查看文章都使用了[editormd](https://pandao.github.io/editor.md/)，使用Markdown 编辑器编辑文章内容，并进行显示

<hr>

## 文章增删查改功能实现

### 查找：

* 首页：通过文章标题模糊搜索
* 管理页面：通过作者信息以及文章标题的模糊搜索查找

<hr>

### 添加了用户管理的相应页面：显示所有用户、添加用户、编辑用户、修改密码

