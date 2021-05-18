<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>フォロー済み従業員一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="follower" items="${followers}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td>${follower.getCode()}</td>
                        <td>${follower.getName()}</td>
                        <td><a href="<c:url value='/follows/reports?id=${follower.getId()}' />">日報一覧</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:param>
</c:import>