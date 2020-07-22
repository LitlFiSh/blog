# blog
### 项目引入的技术和框架
- Web框架：SpringBoot
- 前端模板：Thymeleaf
- 数据库：MySQL
- 数据库ORM：JPA

  <hr>

#### 数据库名默认为**myblog**，其中有`article`表和`user`表；

用户表`user`

| ID     | id       |
| ------ | :------- |
| 用户名 | username |
| 密码   | password |

文章表`article`

| ID           | id             |
| ------------ | -------------- |
| 文章标题     | articleTitle   |
| 文章内容     | articleContent |
| 作者         | author         |
| 发布时间     | publishDate    |
| 最后修改时间 | updateDate     |



<hr>

## 文章增删查改功能实现

### 查找：

* 首页：通过文章标题模糊搜索
* 管理页面：通过作者信息以及文章标题的模糊搜索查找

文章的添加、修改，以及查看文章都使用了[editormd](https://pandao.github.io/editor.md/)，使用Markdown 编辑器编辑文章内容，并进行显示

<hr>
### 当登录用户为`superAdmin`时，导航栏增加用户管理连接，可进行用户的增删改（未测试）

### 增加所有登录用户修改密码的功能（未测试）

> 修改密码需要输入旧密码和两次新密码（两次新密码是否相同的检测还没有实现）

