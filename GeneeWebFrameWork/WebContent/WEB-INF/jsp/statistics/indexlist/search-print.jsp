<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%
	String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + this.getServletContext().getContextPath() + "/" ;
%>
<base href="<%=webPath %>" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>仪器统计列表</title>
<style type="text/css">
	a {
		color:blue;
		text-decoration:underline;
	}
	table.report {
		font-size: 14px;
		margin:auto;
		border-collapse: collapse;
	}
	thead{
		border-top:2px solid	#000;
		border-bottom:1px solid #000;
	}
	tbody{
		border-bottom:2px solid #000;
	}
	thead#stat{
		font-size: 10px;
		border:none;
	}
	table.report caption {
		font-size: 150%;
		margin-bottom: 10px;
	}
	table.report tbody td {
		vertical-align: top;
		border-bottom: 1px dotted #CCC;
	}
	table.report .referred_charge {
		background-color: #eee;
		border:1px dotted #CCC;
	}
	.nowrap {
    		white-space: nowrap;
	}
</style>
<style media="print">
	.buttons{
		display:none;
	}
</style>
<script src="js/jquery/jquery-1.11.1.min.js"></script>
<script src="js/jquery.overlay.js"></script>
<script>
	$(document).ready(function() {
		$.ajaxSetup({   
            async : false  
        }); 
		// 加载等待效果
		$(document).overlayshow({
			img: "icon/loading40.gif"
		});
		var webPath = "<%=webPath %>";
		var printPath = webPath + "statistics/result/print/data";
		var parameter = {
			indexId : $("#indexId").val(),
			eq_name : $("#eq_name").val(),
			eq_type : $("#eq_type").val(),
			eq_org : $("#eq_org").val(),
			eq_contact : $("#eq_contact").val(),
			eq_incharge : $("#eq_incharge").val(),
			lab_org : $("#lab_org").val(),
			lab : $("#lab").val(),
			user : $("#user").val(),
			dstart : $("#dstart").val(),
			dend : $("#dend").val()
		};
		$.post(printPath, parameter, function (data) {
			$("body").empty().append($("<div/>").addClass("buttons").append($("<a/>").attr("href", "#	").html("打印").click(function(){
				window.print(); return false;
			})).append("&nbsp;").append($("<a/>").attr("href", "#").html("关闭").click(function(){
				window.close(); return false;
			})));

			var topHeaders = data.topHeaders;
			var midHeaders = data.midHeaders;
			var contents = data.contents;
			var total = data.total;
			
			var $center = $("<center/>");
			var $table = $("<table/>").attr({
				"cellpadding" : "5",
				"cellspacing" : "5"
			}).addClass("report");
			
			var $caption = $("<caption/>").addClass("nowrap").html("仪器统计列表");
			var $top_row = $("<tr/>");
			var $thead = $("<thead/>");
			var $tbody = $("<tbody/>");
			
			$center.append($table);
			$table.append($caption);
			$table.append($thead);
			$table.append($tbody);
			$thead.append($top_row);
			
			$.each(topHeaders, function(i, top) {
				var indexPlugin = 0;
				if (i == 0) {
					indexPlugin = 2;
				}
				var $top_cell = $("<th/>").addClass("nowrap").attr("colspan", top.ccount + indexPlugin).html(top.name);
				
				
				$top_row.append($top_cell);
			});
			
			
			var $mid_row = $("<tr/>");
			$thead.append($mid_row);
			
			$.each(midHeaders, function(i, mid) {
				var $mid_cell = $("<td/>").html(mid.name);
				
				if (mid.code == "eq_name") {
					var $eq_no_cell = $("<td/>").html("仪器编号");
					$mid_row.append($eq_no_cell);
					
					var $eq_id_cell = $("<td/>").html("仪器CF_ID");
					$mid_row.append($eq_id_cell);
				}
				
				$mid_row.append($mid_cell);
			});
			
			$.each(contents, function(j, cont) {
				var $content_row = $("<tr/>");
				$tbody.append($content_row);
				
				$.each(midHeaders, function(i, mid) {	
					for (var key in cont) {
						if (key == mid.code) {
							var $content_cell = $("<td/>").html(cont[key]);
							$content_row.append($content_cell);
							
							if (mid.code == "eq_name") {
								var $eq_no_cell = $("<td/>").html(cont["eq_ref_no"]);
								$content_row.append($eq_no_cell);
								
								var $eq_id_cell = $("<td/>").html(cont["eq_id"]);
								$content_row.append($eq_id_cell);
							}
							
							break;
						}
					}
					
				});
			});
			
			var $total_row = $("<tr/>");
			$tbody.append($total_row);
			
			var $total_cell_eqcount = $("<td/>").html("总计：仪器台数：" + total.eq_count);
			$total_row.append($total_cell_eqcount);
			
			var $total_cell_eqno = $("<td/>").html("-");
			$total_row.append($total_cell_eqno);
			
			var $total_cell_eqid = $("<td/>").html("-");
			$total_row.append($total_cell_eqid);

			$.each(midHeaders, function(i, mid) {	
				if (mid.code != "eq_name")  {
					var $total_cell = $("<td/>").html("-");
					
					for (var key in total) {
						if (key == mid.code) {
							$total_cell.html(total[key]);
							break;
						}
					}
					
					$total_row.append($total_cell);
				}
			});
			
			$("body").append($center );
        }, "json");
		$(document).overlayhide();
	});
</script>
</head>
<body>
	<input type="hidden" id="indexId" value="<%=request.getParameter("indexId")%>"/>
	<input type="hidden" id="eq_name" value="<%=request.getParameter("eq_name")%>"/>
	<input type="hidden" id="eq_type" value="<%=request.getParameter("eq_type")%>"/>
	<input type="hidden" id="eq_org" value="<%=request.getParameter("eq_org")%>"/>
	<input type="hidden" id="eq_contact" value="<%=request.getParameter("eq_contact")%>"/>
	<input type="hidden" id="eq_incharge" value="<%=request.getParameter("eq_incharge")%>"/>
	<input type="hidden" id="lab_org" value="<%=request.getParameter("lab_org")%>"/>
	<input type="hidden" id="lab" value="<%=request.getParameter("lab")%>"/>
	<input type="hidden" id="user" value="<%=request.getParameter("user")%>"/>
	<input type="hidden" id="dstart" value="<%=request.getParameter("dstart")%>"/>
	<input type="hidden" id="dend" value="<%=request.getParameter("dend")%>"/>
</body>
</html>
