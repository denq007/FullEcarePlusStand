<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header class="navbar">
    <div class="nav-right">

        <sec:authorize access="isAuthenticated()" method="GET">
            <div class="login_container logout_container">
                <a href="<%--${pageContext.request.contextPath}--%>/logout <%--?${_csrf.parameterName}=${_csrf.token}--%>">Logout</a>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </sec:authorize>

    </div>
</header>