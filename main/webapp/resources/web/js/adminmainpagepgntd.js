
let contextName = "/worldwidelogistics";

$(document).ready(function(){
    $("#myInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});



$(document).ready(function(){
    $("#myInput2").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myTable2 tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});



$(document).ready(function(){
    $("#myInput3").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myTable3 tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});



$(document).ready(function(){
    $("#myInput4").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myTable4 tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});


$(document).ready(function(){
    $("#myInput5").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myTable5 tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});


$(document).ready(function(){
    $("#myInput6").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myTable6 tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});

function showMoreOrders() {
    console.log("Gonna show more orders!");
    var req = new XMLHttpRequest();
    console.log("Making onreadystatechangefunction!");
    req.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var ordersArr = JSON.parse(this.responseText);
            for(var i = 0; i < ordersArr.length; i++){
                console.log("ordersArr[i] = " + ordersArr[i]);
                var item = ordersArr[i];
                var tbody = document.getElementById('myTable').getElementsByTagName('TBODY')[0];
                var row = document.createElement("TR");
                tbody.appendChild(row);
                var tdEditBtn = document.createElement("TD");
                var tdDeleteBtn = document.createElement("TD");
                var tdId = document.createElement("TD");
                var tdDescr = document.createElement("TD");
                var tdDate = document.createElement("TD");
                var tdStatus = document.createElement("TD");
                var tdAssignedTruck = document.createElement("TD");
                var tdAssignedDrivers = document.createElement("TD");
                var tdRoute = document.createElement("TD");
                row.appendChild(tdEditBtn);
                row.appendChild(tdDeleteBtn);
                row.appendChild(tdId);
                row.appendChild(tdDescr);
                row.appendChild(tdDate);
                row.appendChild(tdStatus);
                row.appendChild(tdAssignedTruck);
                row.appendChild(tdAssignedDrivers);
                row.appendChild(tdRoute);
                tdEditBtn.innerHTML = "<form action=\"" + contextName + "/adminmainpage/1\" method=\"post\" >\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\">Edit</button>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" hidden name=\"id\" value=\"" + item.id + "\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t</form>";
                // tdDeleteBtn.innerHTML = "<form action=\"${pageContext.request.contextPath}/adminmainpage/2\" method=\"post\" >\n" +
                tdDeleteBtn.innerHTML = "<form action='#'>\n" +
                    "<button type=\"submit\" id=\"del+${order.id}\" class=\"btn btn-danger\" onclick=\"deleteOrder("+item.id+")\">Delete</button>" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" hidden name=\"id\" value=\"" +item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t</form>";
                tdId.innerHTML = item.personalNumber;
                tdDescr.innerHTML = item.description;
                tdDate.innerHTML = item.date;
                tdStatus.innerHTML = item.status;
                if (typeof item.assignedTruckRegistrationNumber === 'undefined') {
                    tdAssignedTruck.innerHTML = "No assigned truck";
                }
                else {
                    tdAssignedTruck.innerHTML = item.assignedTruckRegistrationNumber;
                }
                if (typeof item.assignedDrivers === 'undefined') {
                    tdAssignedDrivers.innerHTML = " <div class=\"dropdown\">\n" +
                        "                            <button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show assigned drivers\n" +
                        "                        <span class=\"caret\"></span></button>\n" +
                        "                        <ul class=\"dropdown-menu\">\n" +
                        "                             <li><a href=\"#\">No assigned drivers</a></li>\n" +
                        "                        </ul>\n" +
                        "                        </div>"
                }
                else {

                    var resultString = " <div class=\"dropdown\">\n" +
                        "                            <button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show assigned drivers\n" +
                        "                        <span class=\"caret\"></span></button>\n" +
                        "                        <ul class=\"dropdown-menu\">\n";
                    for(var j = 0; j < item.assignedDrivers.length; j++){
                        resultString += "                             <li><a href=\"#\">" + item.assignedDrivers[j] + "</a></li>\n"
                    }
                    resultString +=  "                        </ul>\n" +
                        "                        </div>"
                    tdAssignedDrivers.innerHTML = resultString;
                }

                if (typeof item.route === 'undefined'){
                    tdRoute.innerHTML = "No route";
                }
                else{
                    var resultString2 = "<div class=\"dropdown\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show route\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span></button>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu\">\n";

                    for(var k = 0; k < item.route.length; k++) {
                        resultString2 += "                                                                <li><a href=\"#\">" +item.route[k]+ "</a></li>\n";
                    }
                    resultString2 +=     "\t\t\t\t\t\t\t\t\t\t\t\t\t</ul>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t</div>";
                    tdRoute.innerHTML = resultString2;
                }

            }
            var currentOrderPageNum = (Number)(document.getElementById('currentOrderPage').getAttribute('value'));
            var newNum = (Number)(currentOrderPageNum + 1);
            document.getElementById('currentOrderPage').setAttribute('value',newNum);
            // alert("current order page=" + newNum);
        }
    };
    var pageSize = 2; //document.getElementById('currentOrderPage').value();
    var pageNumber = (Number)(document.getElementById('currentOrderPage').getAttribute('value'));
    var necessaryPageNumber = (Number)(pageNumber + 1);
    var reqURL = contextName + '/getpaginatedorderslist?pageSize='+pageSize+'&pageNumber='+necessaryPageNumber;
    req.open("GET", reqURL, true);
    req.send();
}

function deleteOrder(orderId) {
    var sureDelete = confirm("Do you really want to delete this order?");
    if (sureDelete) {
        var req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                var res = JSON.parse(this.responseText);
                if (res === "ERROR_CAN_NOT_DELETE_ORDER_WITH_SUCH_STATUS"){
                    alert("Can not delete order! Error message: " + res);
                }
                else {
                    alert("Order deleted successfully!");
                    location=location;
                }
            }
        }
        var url = contextName +"/deleteorder?orderId=" + orderId;
        req.open('POST', url, true);
        req.send();
    }
}

function showMoreTrucks() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var trucksArr = JSON.parse(this.responseText);
            for(var i = 0; i < trucksArr.length; i++){
                var item = trucksArr[i];
                var tbody = document.getElementById('myTable2').getElementsByTagName('TBODY')[0];
                var row = document.createElement("TR");
                tbody.appendChild(row);
                var tdEditBtn = document.createElement("TD");
                var tdDeleteBtn = document.createElement("TD");
                var tdRegNum = document.createElement("TD");
                var tdNumOfDrivers = document.createElement("TD");
                var tdCapacity = document.createElement("TD");
                var tdState = document.createElement("TD");
                var tdCurrentCity = document.createElement("TD");
                var tdAssignedDrivers = document.createElement("TD");
                var tdOrder = document.createElement("TD");
                row.appendChild(tdEditBtn);
                row.appendChild(tdDeleteBtn);
                row.appendChild(tdRegNum);
                row.appendChild(tdNumOfDrivers);
                row.appendChild(tdCapacity);
                row.appendChild(tdState);
                row.appendChild(tdCurrentCity);
                row.appendChild(tdAssignedDrivers);
                row.appendChild(tdOrder);
                tdEditBtn.innerHTML = "<form action=\""+ contextName+"/adminmainpage/3\" method=\"post\" >\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\">Edit</button>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" hidden name=\"id\" value=\"" + item.id +"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t</form>";


                tdDeleteBtn.innerHTML = "<form action=\"#\" >\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-danger\" onclick=\"deleteTruck("+item.id+")\">Delete</button>\n" +
                    "                                            <input type=\"text\" hidden name=\"id\" value=\"" +item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t</form>";

                tdRegNum.innerHTML = item.registrationNumber;
                tdNumOfDrivers.innerHTML = item.numberOfDrivers;
                tdCapacity.innerHTML = item.capacity;
                tdState.innerHTML = item.state;
                tdCurrentCity.innerHTML = item.currentCity;
                if (typeof item.assignedDriversNames === 'undefined'){
                    tdAssignedDrivers.innerHTML = "<div class=\"dropdown\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show drivers\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span></button>\n" +
                        "\t\t\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<li><a href=\"#\">No assigned drivers</a></li>\n" +
                        "\t\t\t\t\t\t\t\t\t\t</ul>\n" +
                        "\t\t\t\t\t\t\t\t\t</div>";
                }
                else {
                    var driversStr = "<div class=\"dropdown\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show drivers\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span></button>\n" +
                        "\t\t\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu\">\n";
                    for(var j = 0; j < item.assignedDriversNames.length; j++){
                        driversStr += "\t\t\t\t\t\t\t\t\t\t\t\t\t<li><a href=\"#\">" + item.assignedDriversNames[j] + "</a></li>\n";
                    }
                    driversStr +="\t\t\t\t\t\t\t\t\t\t</ul>\n" +
                        "\t\t\t\t\t\t\t\t\t</div>";
                    tdAssignedDrivers.innerHTML = driversStr;

                }
                if (typeof item.assignedOrderDescription === 'undefined'){
                    tdOrder.innerHTML = "No assigned order";
                }
                else {
                    tdOrder.innerHTML = item.assignedOrderDescription;
                }
            }
            var currentTruckPageNum = (Number)(document.getElementById('currentTruckPage').getAttribute('value'));
            var newNum = (Number)(currentTruckPageNum + 1);
            document.getElementById('currentTruckPage').setAttribute('value',newNum);
        }
    };
    var pageSize = 2;
    var pageNumber = (Number)(document.getElementById('currentTruckPage').getAttribute('value'));
    var necessaryPageNumber = (Number)(pageNumber + 1);
    var reqURL = contextName + '/getpaginatedtruckslist?pageSize='+pageSize+'&pageNumber='+necessaryPageNumber;
    req.open("GET", reqURL, true);
    req.send();
}

function deleteTruck(truckId) {
    var sureDelete = confirm("Do you really want to delete this truck?");
    if (sureDelete) {
        var req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                var res = JSON.parse(this.responseText);
                if (res === "TRUCK_DELETED"){
                    alert("Truck deleted successfully!");
                    location=location;
                }
                else {
                    alert("Truck not deleted: " + res);
                }
            }
        }
        var url = contextName + "/deletetruck?truckId=" + truckId;
        req.open('POST', url, true);
        req.send();
    }
}

function showMoreUsers() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var trucksArr = JSON.parse(this.responseText);
            for(var i = 0; i < trucksArr.length; i++){
                var item = trucksArr[i];
                var tbody = document.getElementById('myTable3').getElementsByTagName('TBODY')[0];
                var row = document.createElement("TR");
                tbody.appendChild(row);
                var tdEditBtn = document.createElement("TD");
                var tdDeleteBtn = document.createElement("TD");
                var tdFirstName = document.createElement("TD");
                var tdMiddleName = document.createElement("TD");
                var tdLastName = document.createElement("TD");
                var tdPersonalNumber = document.createElement("TD");
                var tdRole = document.createElement("TD");
                var tdHoursWorked = document.createElement("TD");
                var tdDriverStatus = document.createElement("TD");
                var tdCity = document.createElement("TD");
                var tdTruck = document.createElement("TD");
                var tdOrder = document.createElement("TD");
                row.appendChild(tdEditBtn);
                row.appendChild(tdDeleteBtn);
                row.appendChild(tdFirstName);
                row.appendChild(tdMiddleName);
                row.appendChild(tdLastName);
                row.appendChild(tdPersonalNumber);
                row.appendChild(tdRole);
                row.appendChild(tdHoursWorked);
                row.appendChild(tdDriverStatus);
                row.appendChild(tdCity);
                row.appendChild(tdTruck);
                row.appendChild(tdOrder);
                tdEditBtn.innerHTML = "<form action=\""+ contextName+"/adminmainpage/5\" method=\"post\" >\n" +
                    "                                            <button type=\"submit\" class=\"btn btn-primary\">Edit</button>\n" +
                    "                                            <input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "                                        </form>";


                tdDeleteBtn.innerHTML = "<form action=\"#\"  >\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-danger\" onclick=\"deleteUser("+item.id+")\">Delete</button>\n" +
                    "                                            <input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t</form>";

                tdFirstName.innerHTML = item.firstName;
                tdMiddleName.innerHTML = item.middleName;
                tdLastName.innerHTML = item.lastName;
                tdPersonalNumber.innerHTML = item.personalNumber;
                tdRole.innerHTML = item.role;
                tdHoursWorked.innerHTML = item.hoursWorked;
                tdDriverStatus.innerHTML = item.driverStatus;
                tdCity.innerHTML = item.currentCityName;
                tdTruck.innerHTML = item.currentTruckRegistrationNumber;
                tdOrder.innerHTML = item.orderDescription;
            }
            var currentUserPageNum = (Number)(document.getElementById('currentUserPage').getAttribute('value'));
            var newNum = (Number)(currentUserPageNum + 1);
            document.getElementById('currentUserPage').setAttribute('value',newNum);
        }
    };
    var pageSize = 2; //document.getElementById('currentOrderPage').value();
    var pageNumber = (Number)(document.getElementById('currentUserPage').getAttribute('value'));
    var necessaryPageNumber = (Number)(pageNumber + 1);
    var reqURL = contextName + '/getpaginateduserslist?pageSize='+pageSize+'&pageNumber='+necessaryPageNumber;
    req.open("GET", reqURL, true);
    req.send();
}

function deleteUser(userId) {
    var sureDelete = confirm("Do you really want to delete this user?");
    if (sureDelete) {
        var req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                var res = JSON.parse(this.responseText);
                if (res === "USER_DELETED"){
                    alert("User succesfully deleted!");
                    location=location;
                }
                else {
                    alert("User not deleted:" + res);
                }
            }
        }
        var url = contextName + "/deleteuser?userId=" + userId;
        req.open('POST', url, true);
        req.send();
    }
}

function showMoreCargos() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var cargosArr = JSON.parse(this.responseText);
            for(var i = 0; i < cargosArr.length; i++){
                var item = cargosArr[i];
                var tbody = document.getElementById('myTable4').getElementsByTagName('TBODY')[0];
                var row = document.createElement("TR");
                tbody.appendChild(row);
                var tdEditBtn = document.createElement("TD");
                var tdDeleteBtn = document.createElement("TD");
                var tdPersonalNumber = document.createElement("TD");
                var tdName = document.createElement("TD");
                var tdWeight = document.createElement("TD");
                var tdCityFrom = document.createElement("TD");
                var tdCityTo = document.createElement("TD");
                var tdStatus = document.createElement("TD");
                row.appendChild(tdEditBtn);
                row.appendChild(tdDeleteBtn);
                row.appendChild(tdPersonalNumber);
                row.appendChild(tdName);
                row.appendChild(tdWeight);
                row.appendChild(tdCityFrom);
                row.appendChild(tdCityTo);
                row.appendChild(tdStatus);

                tdEditBtn.innerHTML = "<form action=\""+contextName +"/adminmainpage/7\" method=\"post\" >\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\">Edit</button>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t</form>";


                tdDeleteBtn.innerHTML = "<form action=\"#\" >\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-danger\" onclick=\"deleteCargo("+item.id+")\">Delete</button>\n" +
                    "                                            <input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t</form>";

                tdPersonalNumber.innerHTML = item.personalNumber;
                tdName.innerHTML = item.name;
                tdWeight.innerHTML = item.weight;
                tdCityFrom.innerHTML = item.cityFrom;
                tdCityTo.innerHTML = item.cityTo;
                tdStatus.innerHTML = item.status;

            }
            var currentCargoPageNum = (Number)(document.getElementById('currentCargoPage').getAttribute('value'));
            var newNum = (Number)(currentCargoPageNum + 1);
            document.getElementById('currentCargoPage').setAttribute('value',newNum);
            // alert("current cargo page=" + newNum);
        }
    };
    var pageSize = 2; //document.getElementById('currentOrderPage').value();
    var pageNumber = (Number)(document.getElementById('currentCargoPage').getAttribute('value'));
    var necessaryPageNumber = (Number)(pageNumber + 1);
    var reqURL =contextName + '/getpaginatedcargoslist?pageSize='+pageSize+'&pageNumber='+necessaryPageNumber;
    req.open("GET", reqURL, true);
    req.send();
}

function deleteCargo(cargoId) {
    var sureDelete = confirm("Do you really want to delete this cargo?");
    if (sureDelete) {
        var req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                var res = JSON.parse(this.responseText);
                if (res === "CARGO_DELETED"){
                    alert("Cargo succesfully deleted!");
                    location=location;
                }
                else {
                    alert("Cargo not deleted:" + res);
                }
            }
        }
        var url = contextName + "/deletecargo?cargoId=" + cargoId;
        req.open('POST', url, true);
        req.send();
    }
}

function showMoreCities() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var citiesArr = JSON.parse(this.responseText);
            for(var i = 0; i < citiesArr.length; i++){
                var item = citiesArr[i];
                var tbody = document.getElementById('myTable5').getElementsByTagName('TBODY')[0];
                var row = document.createElement("TR");
                tbody.appendChild(row);
                var tdEditBtn = document.createElement("TD");
                var tdDeleteBtn = document.createElement("TD");
                var tdName = document.createElement("TD");
                var tdHasAgency = document.createElement("TD");
                var tdDrivers = document.createElement("TD");
                var tdTrucks = document.createElement("TD");

                row.appendChild(tdEditBtn);
                row.appendChild(tdDeleteBtn);
                row.appendChild(tdName);
                row.appendChild(tdHasAgency);
                row.appendChild(tdDrivers);
                row.appendChild(tdTrucks);

                tdEditBtn.innerHTML = " <form action=\""+contextName+"/adminmainpage/9\" method=\"post\" >\n" +
                    "\t\t\t\t\t\t\t\t\t <button type=\"submit\" class=\"btn btn-primary\">Edit</button>\n" +
                    "\t\t\t\t\t\t\t\t\t <input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t </form>";


                tdDeleteBtn.innerHTML = "<form action=\"#\" >\n" +
                    "\t\t\t\t\t\t\t\t\t <button type=\"submit\" class=\"btn btn-danger\" onclick=\"deleteCity("+item.id+")\">Delete</button>\n" +
                    "\t\t\t\t\t\t\t\t\t <input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t </form>";

                tdName.innerHTML = item.name;
                tdHasAgency.innerHTML = item.hasAgency;
                if (typeof item.driversInCity === 'undefined'){
                    tdDrivers.innerHTML = " <div class=\"dropdown\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t <button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show drivers\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t <span class=\"caret\"></span></button>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t <ul class=\"dropdown-menu\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t <li><a href=\"#\">No drivers in city</a></li>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t </ul>\n" +
                        "\t\t\t\t\t\t\t\t\t\t </div>";
                }
                else{
                    var driversString = "<div class=\"dropdown\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show drivers\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span></button>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu\">";

                    for(var j = 0; j < item.driversInCity.length; j++){
                        driversString+="<li><a href=\"#\">"+item.driversInCity[j]+"</a></li>";
                    }
                    driversString+="</ul>\n" +
                        "\t\t\t\t\t\t\t\t\t\t</div>";
                    tdDrivers.innerHTML = driversString;
                }
                if (typeof item.trucksInCity === 'undefined'){
                    tdTrucks.innerHTML = "<div class=\"dropdown\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t <button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show trucks\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t <span class=\"caret\"></span></button>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t <ul class=\"dropdown-menu\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t <li><a href=\"#\">No trucks in city</a></li>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t </ul>\n" +
                        "\t\t\t\t\t\t\t\t\t\t </div>";
                }
                else {
                    var trucksString = "<div class=\"dropdown\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t <button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Show trucks\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t <span class=\"caret\"></span></button>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t <ul class=\"dropdown-menu\">";
                    for(var k=0; k < item.trucksInCity.length; k++){
                        trucksString+="<li><a href=\"#\">"+item.trucksInCity[k]+"</a></li>";
                    }
                    trucksString+="</ul>\n" +
                        "\t\t\t\t\t\t\t\t\t\t </div>";
                    tdTrucks.innerHTML = trucksString;
                }
            }
            var currentCityPageNum = (Number)(document.getElementById('currentCityPage').getAttribute('value'));
            var newNum = (Number)(currentCityPageNum + 1);
            document.getElementById('currentCityPage').setAttribute('value',newNum);
        }
    };
    var pageSize = 2; //document.getElementById('currentOrderPage').value();
    var pageNumber = (Number)(document.getElementById('currentCityPage').getAttribute('value'));
    var necessaryPageNumber = (Number)(pageNumber + 1);
    var reqURL = contextName +'/getpaginatedcitieslist?pageSize='+pageSize+'&pageNumber='+necessaryPageNumber;
    req.open("GET", reqURL, true);
    req.send();
}

function deleteCity(cityId) {
    var sureDelete = confirm("Do you really want to delete this city?");
    if (sureDelete) {
        var req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                var res = JSON.parse(this.responseText);
                if (res === "CITY_DELETED"){
                    alert("City succesfully deleted!");
                    location=location;
                }
                else {
                    alert("City not deleted: " + res);
                }
            }
        };
        var url = contextName + "/deletecity?cityId=" + cityId;
        req.open('POST', url, true);
        req.send();
    }
}


function showMoreRoutes() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var routesArr = JSON.parse(this.responseText);
            for(var i = 0; i < routesArr.length; i++){
                var item = routesArr[i];
                var tbody = document.getElementById('myTable6').getElementsByTagName('TBODY')[0];
                var row = document.createElement("TR");
                tbody.appendChild(row);
                var tdEditBtn = document.createElement("TD");
                var tdDeleteBtn = document.createElement("TD");
                var tdCityFrom = document.createElement("TD");
                var tdCityTo = document.createElement("TD");
                var tdDistance = document.createElement("TD");
                row.appendChild(tdEditBtn);
                row.appendChild(tdDeleteBtn);
                row.appendChild(tdCityFrom);
                row.appendChild(tdCityTo);
                row.appendChild(tdDistance);

                tdEditBtn.innerHTML ="<form action=\""+contextName+"/adminmainpage/11\" method=\"post\" >\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\">Edit</button>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t</form>";


                tdDeleteBtn.innerHTML = "<form action=\"#\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-danger\" onclick=\"deleteRoute("+item.id+")\">Delete</button>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" hidden name=\"id\" value=\""+item.id+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t</form>";
                tdCityFrom.innerHTML = item.cityFrom;
                tdCityTo.innerHTML = item.cityTo;
                tdDistance.innerHTML = item.distance;
            }
            var currentRoutePageNum = (Number)(document.getElementById('currentRoutePage').getAttribute('value'));
            var newNum = (Number)(currentRoutePageNum + 1);
            document.getElementById('currentRoutePage').setAttribute('value',newNum);
        }
    };
    var pageSize = 2; //document.getElementById('currentOrderPage').value();
    var pageNumber = (Number)(document.getElementById('currentRoutePage').getAttribute('value'));
    var necessaryPageNumber = (Number)(pageNumber + 1);
    var reqURL = contextName + '/getpaginatedrouteslist?pageSize='+pageSize+'&pageNumber='+necessaryPageNumber;
    req.open("GET", reqURL, true);
    req.send();
}

function deleteRoute(routeId) {
    var sureDelete = confirm("Do you really want to delete this route?");
    if (sureDelete) {
        var req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                var res = JSON.parse(this.responseText);
                if (res === "ROUTE_DELETED"){
                    alert("Route succesfully deleted!");
                    location=location;
                }
                else {
                    alert("Route not deleted: " + res);
                }
            }
        };
        var url = contextName + "/deleteroute?routeId=" + routeId;
        req.open('POST', url, true);
        req.send();
    }
}