<%@page import="java.util.List"%>
<%@page import="domain.Product"%>
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
								<li><a href="ProductPaginationServlet?currentPage=1">Product</a></li>
								<li><a href="cart.jsp">Cart</a>
								<li><a href="LogoutServlet">Logout</a></li>
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

	<div class="content-section">
		<main class='container' role='main'>
			<div class='container' style="margin-top: 60px">
				<h2>Products list</h2>
				<div class='row' id="tableContent">
					<div class='col-xs-12'>
						<div class='table-responsive orders-list'>
							<table class='table table-striped table-hover'>
								<%
								int currentPage = (int) request.getAttribute("currentPage");
								int nOfPages = (int) request.getAttribute("nOfPages");
								%>
								<input type="hidden" name="currentPage" value="<%=currentPage%>" />
								<thead>
									<tr>
										<th>Product Code</th>
										<th>buy price</th>
										<th>MSRP</th>
										<th>description</th>
										<th>name</th>
										<th>scale</th>
										<th>vendor</th>
										<th>in stock</th>
										<th>Add</th>
									</tr>
								</thead>
								<tbody>
									<%
									List<Product> products = (List<Product>) request.getAttribute("products");
									if (products.size() != 0) {
										for (Product t : products) {
											out.println("<tr>");
											out.println("<td>" + t.getProductcode() + "</td>");
											out.println("<td>" + t.getBuyprice() + "</td>");
											out.println("<td>" + t.getMsrp() + "</td>");
											out.println("<td>" + t.getProductdescription() + "</td>");
											out.println("<td>" + t.getProductname() + "</td>");
											out.println("<td>" + t.getProductscale()+ "</td>");
											out.println("<td>" + t.getProductvendor() + "</td>");
											out.println("<td>" + t.getQuantityinstock() + "</td>");
											out.println("<td><a href=\"CartController?id=" + t.getProductcode() + "\">Add</a></td>");
											out.println("</tr>");
										}

									} else {
										out.println("<tr>");
										String status = "No records";
										for (int i = 0; i < 8; i++) {
											out.println("<td>" + status + "</td>");
										}
										out.println("</tr>");
										
									}
									%>
									<!-- <tr>
								<td><a href="/orders/ORD-12">ORD-12</a></td>
								<td>Pizza Order</td>
								<td><span><i class='fa fa-gears'></i> In progress</span></td>
								<td>03/09/2021 13:10 PM</td>
								<td>David Jones</td>
								<td></td>
								<td></td>
							</tr> -->
								<tbody>
							</table>
						</div>
					</div>
					<nav aria-label="Navigation for staffs">
						<ul class="pagination">

							<%
							if (currentPage != 1 && nOfPages != 0) {
							%>

							<%
							out.println("<li class=\"page-item\">");
							out.println("<a class=\"page-link\" href=\"" + "ProductPaginationServlet?recordsPerPage=" + 10 + "&currentPage=1"
									+ "\">First</a>");
							out.println("</li>");
							%>


							<li class="page-item">
								<%
								out.println("<a class=\"page-link\" href=\"" + "ProductPaginationServlet?recordsPerPage=" + 10 + "&currentPage="
										+ (currentPage - 1) + "\">Previous</a>");
								%>
							</li>
							<%
							}
							%>
							<%
							if (currentPage < nOfPages) {
								out.println("<li class=\"page-item\">");
								out.println("<a class=\"page-link\" href=\"" + "ProductPaginationServlet?recordsPerPage=" + 10 + "&currentPage="
								+ (currentPage + 1) + "\">Next</a>");
								out.println("</li>");

								out.println("<li class=\"page-item\">");
								out.println("<a class=\"page-link\" href=\"" + "ProductPaginationServlet?recordsPerPage=" + 10 + "&currentPage=" + nOfPages
								+ "\">Last</a>");
								out.println("</li>");
							}
							%>

						</ul>
					</nav>
					<%
					if (nOfPages != 0) {
						out.println("<p class=\"pageref\">");
						out.println(currentPage + " of " + nOfPages);
						out.println("</p>");
					}

					//out.println("Text of Text");
					%>

				</div>

			</div>
	</div>
	</main>
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