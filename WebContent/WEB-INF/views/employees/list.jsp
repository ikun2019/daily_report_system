<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>従業員一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>フォロー</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${employee.delete_flag == 1}">
                                    (削除済み)
                                </c:when>
                                <c:when test="${followers.contains(employee.getCode())}">
                                    <label for="followCancelForm${employee.id}"><i class="fas fa-heart" style="color: red;"></i></label>
                                    <form method="POST" name="followForm${employee.id}" action="<c:url value='/follows/destroy' />">
                                        <input type="hidden" name="_token" value="${_token}" />
                                        <input type="hidden" name="employeeCode" value="${employee.code}" />
                                        <input type="submit" id="followCancelForm${employee.id}" style="display: none;" />
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <label for="followForm${employee.id}"><i class="far fa-heart" style="color: red;"></i></label>
                                    <form method="POST" name="followForm${employee.id}" action="<c:url value='/follows/create' />">
                                        <input type="hidden" name="_token" value="${_token}" />
                                        <input type="hidden" name="followCode" value="${employee.code}" />
                                        <input type="submit" id="followForm${employee.id}" style="display: none;" />
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            (全 ${employees_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15 + 1)}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/list?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/' />">日報一覧に戻る</a></p>
    </c:param>
</c:import>