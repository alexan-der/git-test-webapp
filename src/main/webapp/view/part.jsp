<%@ page import="alexander.zabenkov.business.entity.Part" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Parts</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/static/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/bootstrap-datepicker3.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/style.css" />" rel="stylesheet">
    <script src="<c:url value="/static/js/jquery-3.3.1.min.js" />" ></script>
    <script src="<c:url value="/static/js/bootstrap-datepicker.min.js" />" ></script>
    <script src="<c:url value="/static/js/bootstrap-datepicker.en-GB.min.js" />" charset="UTF-8"></script>
</head>
<body>
    <div class="container">
        <div class="offset-md-1 col-md-10 align-content-center">
            <h2>Parts List</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th class="sorting-col"><a name="part_number">PN</a></th>
                        <th class="sorting-col"><a name="part_name">Name</a></th>
                        <th class="sorting-col"><a name="vendor">Vendor</a></th>
                        <th class="sorting-col" width="9%"><a name="qty">Qty</a></th>
                        <th class="sorting-col"><a name="shipped">Shipped</a></th>
                        <th class="sorting-col"><a name="receive">Receive</a></th>
                    </tr>
                    <tr>
                        <th class="p-2"><input type="text" class="form-control filter" id="part_number" placeholder="PN"></th>
                        <th class="p-2"><input type="text" class="form-control filter" id="part_name" placeholder="Name"></th>
                        <th class="p-2"><input type="text" class="form-control filter" id="vendor" placeholder="Vendor"></th>
                        <th class="p-2"><input type="text" class="form-control filter" id="qty" placeholder="Qty"></th>
                        <th class="p-2">
                            <div class="input-daterange input-group">
                                <input type="text" class="input-sm form-control filter" id="shippedAfter" placeholder="From" />
                                <input type="text" class="input-sm form-control filter" id="shippedBefore" placeholder="To" />
                            </div>
                        </th>
                        <th class="p-2">
                            <div class="input-daterange input-group">
                                <input type="text" class="input-sm form-control filter" id="receiveAfter" placeholder="From" />
                                <input type="text" class="input-sm form-control filter" id="receiveBefore" placeholder="To" />
                            </div>
                        </th>
                    </tr>
                </thead>
                <tbody id="partsTBody">
                <c:forEach items="${partsList}" var="part" >
                    <tr>
                        <td>${part.number}</td>
                        <td>${part.name}</td>
                        <td>${part.vendor}</td>
                        <td>${part.qty}</td>
                        <td>${part.shipped}</td>
                        <td>${part.receive}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
<script>
    $('.input-daterange').datepicker({
        format: "yyyy-mm-dd"
    });
    $('.sorting-col').click(function (e) {
        $('.sorting-col').each(function () {
            if (this != e.currentTarget) {
                $(this).removeClass('up');
                $(this).removeClass('down');
            } else {
                if ( $(this).hasClass('up') || $(this).hasClass('down') ) {
                    $(this).toggleClass('up down');
                } else {
                    $(this).addClass('up');
                }
            }
        });
        updateData();
    });
    $('.filter').change(function(){
        updateData();
    });
    $('.filter').keyup(function(){
        updateData();
    });
    function updateData() {
        $.post("/",
            {'filters' : JSON.stringify(getFilterValues())},
            function(data, status){
                    updateTable(data);
            }
        );
    }
    function getFilterValues() {
        var column = $('.up').length>0?$('.up'):$('.down');
        var order = $('.up').length>0?'ASC':'DESC';

        filters = {
            'part_name' : $('#part_name').val(),
            'part_number' : $('#part_number').val(),
            'vendor' : $('#vendor').val(),
            'qty' : $('#qty').val(),
            'shippedAfter' : $('#shippedAfter').val(),
            'shippedBefore' : $('#shippedBefore').val(),
            'receiveAfter' : $('#receiveAfter').val(),
            'receiveBefore' : $('#receiveBefore').val(),
            'sortingColumn' : column.children().attr('name'),
            'sortingOrder' : order
        };

        return filters;
    }
    function updateTable(data) {
        $('#partsTBody').html("");
        var content = '';
        data.forEach(function(part){
            content +=
            "<tr>" +
            "   <td>"+_p(part.number)+"</td>" +
            "   <td>"+_p(part.name)+"</td>" +
            "   <td>"+_p(part.vendor)+"</td>" +
            "   <td>"+_p(part.qty)+"</td>" +
            "   <td>"+_p(part.shipped)+"</td>" +
            "   <td>"+_p(part.receive)+"</td>" +
            "</tr>";
        });
        $('#partsTBody').html(content);
    }

    function _p(val) {
        if (val === undefined)
            return '';
        else
            return val;
    }
</script>
</html>
