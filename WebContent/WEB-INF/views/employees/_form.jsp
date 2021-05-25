<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" />
        </c:forEach>
    </div>
</c:if>

<label for="code">社員番号</label><br />
<input type="text" name="code" value="${employee.code}" />
<br /><br />

<label for="name">氏名</label><br />
<input type="text" name="name" value="${employee.name}" />
<br /><br />

<label for="group_id">所属部署</label><br />
<select name="group_id">
    <option value="営業部">営業部</option>
    <option value="総務部">総務部</option>
    <option value="人事部">人事部</option>
    <option value="経理部">経理部</option>
    <option value="その他">その他</option>
</select>
<br /><br />

<label for="position">役職</label><br />
<select name="position">
    <option value="4">役員</option>
    <option value="3">部長</option>
    <option value="2">会長</option>
    <option value="1">一般</option>
</select>
<br /><br />

<label for="password">パスワード</label><br />
<input type="password" name="password" />
<br /><br />

<label for="admin_flag">権限</label><br />
<select name="admin_flag">
    <option value="0" <c:if test="${employee.admin_flag == 0}">selected</c:if>>一般</option>
    <option value="1" <c:if test="${employee.admin_flag == 1}">selected</c:if>>管理者</option>
</select>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>