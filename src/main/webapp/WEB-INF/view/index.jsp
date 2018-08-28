<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Languages</title>

	<!-- main style -->
	<link href="../css/index.css" rel="stylesheet">
	
	<!-- components styles -->
	<link href="../css/components/header.css" rel="stylesheet">
	<link href="../css/components/footer.css" rel="stylesheet">
	
	<!-- utilities style -->
	<link href="../css/util.css" rel="stylesheet">
	
</head>
<body>
	<!-- Header component -->
	<jsp:include page="../components/header.jsp" />
	
	<main>
	
		<section class="content">
		
			<div class="content__table">
			
			<!-- //////////////// ERROR MESSAGES /////////////////////////// -->
			
				<c:if test="${findAllException != null}"> 	
					<div class="error">
						<p>
							<c:out value="${findAllException}" />
						</p>
					</div>
				</c:if>
				
				<c:if test="${updateException != null}"> 	
					<div class="error">
						<p>
							<c:out value="${updateException}" />
						</p>
					</div>
				</c:if>
				
				<% if (request.getParameter("error") != null) { %>
					<div class="error">
						<p>
							Unexpected error. try again later.
						</p>
					</div>
				<% } %>
		
		<!-- //////////////////////////////////////////// -->
		
				<div class="table__header">
					<div class="table__row">
						<div class="row__data">
							<div class="row__celd"><span class="celd__header">ID</span></div>
							<div class="row__celd"><span class="celd__header">Name</span></div>
						</div>
					</div>
				</div>
			
				<div class="table__body">
					<c:forEach var="language" items="${languages}">
						<div class="table__row">
							<div class="row__data">
								<div class="row__celd">
									<span> <c:out value="${language.getId()}" /> </span>
								</div>
								<div class="row__celd">
									<span> <c:out value="${language.getName()}" /> </span>
								</div>
							</div>
							
							<div class="row__options">
								<div class="row__celd">
									<a class="celd__button" href="/languages/update?id=${language.getId()}">Update</a>
								</div>
								<div class="row__celd">
									<form action="/languages/deleteLanguageById/${language.getId()}" method="POST">
										<input class="celd__button" type="submit" value="Delete" />
									</form>
								</div>
								
								<c:if test="${deleteException != null && deleteId == language.getId()}"> 
									<div class="error">
										<p>
											<c:out value="${deleteException}" />
										</p>
									</div>
								</c:if>
								
							</div>
						</div>
					</c:forEach>
				</div>	
				
				<form class="table__footer" action="/languages" method="POST">
				
					<div class="table__row">
						<div class="row__data">
							<div class="row__celd">
								<input type="text" name="name" id="name" maxlength="30" placeholder="language name" />
							</div>
						</div>
						
						<div class="row__options">
							<input class="celd__button" type="submit" value="Create">
						</div>
						
						<c:if test="${createException != null}"> 
							<div class="error">
								<p>
									<c:out value="${createException}" />
								</p>
							</div>
						</c:if>
					</div>
					
				</form>
			
			</div>		

		</section>
	
	</main>
	
	<!-- Footer component -->
	<jsp:include page="../components/footer.jsp" />
</body>
</html>