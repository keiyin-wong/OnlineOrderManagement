<%@page import="java.util.List"%>
<%@page import="domain.Order"%>
<%@page import="domain.User"%>
<%@page import="domain.Employee"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<!-- 
Kool Store Template
http://www.templatemo.com/preview/templatemo_428_kool_store
-->
<meta charset="utf-8">
<title>Kool Store - Responsive eCommerce Template</title>

<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800"
	rel="stylesheet">

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/normalize.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/templatemo-misc.css">
<link rel="stylesheet" href="css/templatemo-style.css">

<script src="js/vendor/modernizr-2.6.2.min.js"></script>

</head>
<body>
	<!--[if lt IE 7]>
    <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
    <![endif]-->
<%
		String admin = (String) session.getAttribute("admin");
	%>

	<header class="site-header">

		<div class="main-header">
			<div class="container">
				<div class="row">
					<div class="col-md-4 col-sm-6 col-xs-8">
						<div class="logo">
							<h1>
								<a href="index.html">Kool Store</a>
							</h1>
						</div>
						<!-- /.logo -->
					</div>
					<!-- /.col-md-4 -->
					<div class="col-md-8 col-sm-6 col-xs-4">
						<div class="main-menu">
							<a href="#" class="toggle-menu"> <i class="fa fa-bars"></i>
							</a>
							<ul class="menu">
								<li><a href="EmployeeOrderServlet?currentPage=1">Order</a></li>
								<li><a href="EmployeeEditPersonalInformation">Personal Information</a></li>
								<li><a href="EmployeeProductServlet?currentPage=1">Product</a></li>
								<li><a href="PaymentPaginationServlet?recordsPerPage=10 &currentPage=1">Payment</a></li>
								<%
									if(admin!=null){
								%>
								<li><a href="AdminEmployeeServlet?currentPage=1">Employee</a></li>
								<%} %>
								<li><a href="LogoutServlet">Logout</a></li>
							</ul>
							</ul>
						</div>
						<!-- /.main-menu -->
					</div>
					<!-- /.col-md-8 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.main-header -->

	</header>
	<!-- /.site-header -->

	<div class="container">
		<div class="row">
			<h2 class="col-sm-8 col-sm-offset-2">Employee Information</h2>
		</div>
		<div class="row">
			<%
			Employee employee = (Employee) session.getAttribute("employee");
			%>
			<%
			if(employee!= null){
			%>
			<form class="simple_form form-horizontal order-edit-form col-sm-6"
				id="edit_order_16" action="EmployeeEditPersonalInformation"
				accept-charset="UTF-8" method="post">
				<div class="form-group row string optional order_employeeFirstName">
					<label class="string optional col-sm-4 control-label">Employee
						Number</label>
					<div class="col-sm-8">
						<div class="string optional form-control"><%= employee.getEmployeenumber() %></div>
						<input value=<%= employee.getEmployeenumber() %>
							class="string optional form-control" type="hidden"
							name="empNumber" required>
					</div>
				</div>
				
				<div class="form-group row string optional order_employeeFirstName">
					<label class="string optional col-sm-4 control-label">First
						Name</label>
					<div class="col-sm-8">
						<input value=<%=employee.getFirstname()%>
							class="string optional form-control" type="text"
							name="empFirstName" required>
					</div>
				</div>
				<div class="form-group row string optional order_employeeLastName">
					<label class="string optional col-sm-4 control-label">Last
						Name</label>
					<div class="col-sm-8">
						<input value=<%=employee.getLastname()%>
							class="string optional form-control" type="text"
							name="empLastName" required>
					</div>
				</div>
				<div class="form-group row string optional order_employeeEmail">
					<label class="string optional col-sm-4 control-label">E-mail</label>
					<div class="col-sm-8">
						<input value=<%=employee.getEmail()%>
							class="string optional form-control" type="email" name="empEmail">
					</div>
				</div>
				<div class="form-group row string optional order_motivationText">
					<label class="string optional col-sm-4 control-label">Extension</label>
					<div class="col-sm-8">
						<input value=<%=employee.getExtension()%>
							class="string optional form-control" type="text"
							name="empExtension">
					</div>
				</div>
				<div class="form-group row string optional order_resolution">
					<label class="string optional col-sm-4 control-label">Office
						Code</label>
					<div class="col-sm-8">
						<input class="string optional form-control" type="number" min="1"
							max="7"
							value=<%= employee.getOffice().getOfficecode().toString() %>
							name="empOfficeCode" required>
					</div>
				</div>
				<div class="form-group row string optional order_resolutionText">
					<label class="string optional col-sm-4 control-label">Reportsto</label>
					<div class="col-sm-8">
						<input class="string optional form-control" type="text"
							value=<%=employee.getReportsto()%> name="empReportsto">
					</div>
				</div>
				<div class="form-group row string optional order_adjustResult">
					<label class="string optional col-sm-4 control-label">Job
						Title</label>
					<div class="col-sm-8">
						<input class="string optional form-control" type="text"
							value="<%= employee.getJobtitle().toString()%>"
							name="empJobTitle">
					</div>
				</div>
				<footer class="row">
					<div class="col-sm-8 col-sm-offset-4">
						<input type="submit" name="commit" value="Update Information"
							class="btn btn-primary">
					</div>
				</footer>
				<%
				} else {
				%>
					<h1>No Record Found</h1>
				<%
				}
				%>
				
			</form>
		</div>
	</div>

	<!-- /.content-section -->



	<footer class="site-footer">
		<div class="our-partner">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="customNavigation">
							<a class="btn prev"><i class="fa fa-angle-left"></i></a> <a
								class="btn next"><i class="fa fa-angle-right"></i></a>
						</div>
						<div id="owl-demo" class="owl-carousel">
							<div class="item">
								<a href="#"><img src="images/tm-170x80-1.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-2.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-1.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-2.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-1.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-2.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-1.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-2.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-1.jpg" alt=""></a>
							</div>
							<div class="item">
								<a href="#"><img src="images/tm-170x80-2.jpg" alt=""></a>
							</div>
						</div>
						<!-- /#owl-demo -->
					</div>
					<!-- /.col-md-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.our-partner -->
		<div class="main-footer">
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<div class="footer-widget">
							<h3 class="widget-title">About Us</h3>
							Lorem ipsum dolor sit amet, consectetur adipisicing elit.
							Eligendi, debitis recusandae.
							<ul class="follow-us">
								<li><a href="#"><i class="fa fa-facebook"></i>Facebook</a></li>
								<li><a href="#"><i class="fa fa-twitter"></i>Twitter</a></li>
							</ul>
							<!-- /.follow-us -->
						</div>
						<!-- /.footer-widget -->
					</div>
					<!-- /.col-md-3 -->
					<div class="col-md-3">
						<div class="footer-widget">
							<h3 class="widget-title">Why Choose Us?</h3>
							Kool Store is free responsive eCommerce template provided by
							templatemo website. You can use this layout for any website. <br>
							<br>Tempore cum mollitia eveniet laboriosam corporis
							voluptas earum voluptate. Lorem ipsum dolor sit amet. <br> <br>Credit
							goes to <a rel="nofollow" href="http://unsplash.com">Unsplash</a>
							for all images.
						</div>
						<!-- /.footer-widget -->
					</div>
					<!-- /.col-md-3 -->
					<div class="col-md-2">
						<div class="footer-widget">
							<h3 class="widget-title">Useful Links</h3>
							<ul>
								<li><a href="#">Our Shop</a></li>
								<li><a href="#">Partners</a></li>
								<li><a href="#">Gift Cards</a></li>
								<li><a href="#">About Us</a></li>
								<li><a href="#">Help</a></li>
							</ul>
						</div>
						<!-- /.footer-widget -->
					</div>
					<!-- /.col-md-2 -->
					<div class="col-md-4">
						<div class="footer-widget">
							<h3 class="widget-title">Our Newsletter</h3>
							<div class="newsletter">
								<form action="#" method="get">
									<p>Sign up for our regular updates to know when new
										products are released.</p>
									<input type="text" title="Email" name="email"
										placeholder="Your Email Here"> <input type="submit"
										class="s-button" value="Submit" name="Submit">
								</form>
							</div>
							<!-- /.newsletter -->
						</div>
						<!-- /.footer-widget -->
					</div>
					<!-- /.col-md-4 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.main-footer -->
		<div class="bottom-footer">
			<div class="container">
				<div class="row">
					<div class="col-md-12 text-center">
						<span>Copyright &copy; 2084 <a href="#">Company Name</a> |
							Design: <a href="http://www.templatemo.com">templatemo</a></span>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
							Accusantium, expedita soluta mollitia accusamus ut architecto
							maiores cum fugiat. Pariatur ipsum officiis fuga deleniti alias
							quia nostrum veritatis enim doloremque eligendi?</p>
					</div>
					<!-- /.col-md-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.bottom-footer -->
	</footer>
	<!-- /.site-footer -->


	<script src="js/vendor/jquery-1.10.1.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/vendor/jquery-1.10.1.min.js"><\/script>')
	</script>
	<script src="js/jquery.easing-1.3.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/plugins.js"></script>
	<script src="js/main.js"></script>


	<!-- Google Map -->
	<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
	<script src="js/vendor/jquery.gmap3.min.js"></script>

	<!-- Google Map Init-->
	<script type="text/javascript">
		jQuery(function($) {
			$('.first-map, .map-holder').gmap3({
				marker : {
					address : '40.7828839,-73.9652425'
				},
				map : {
					options : {
						zoom : 15,
						scrollwheel : false,
						streetViewControl : true
					}
				}
			});
		});
	</script>


</body>
</html>