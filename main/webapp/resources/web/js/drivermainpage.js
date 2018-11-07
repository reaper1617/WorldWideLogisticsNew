let contextName = "/worldwidelogistics";

$(document).ready(function(){
    $("#myCargoDetailsInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myCargoDetailsTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});




function updateDriverStatus(driverId) {
    var statusesArr = ["FREE", "RESTING","DRIVING", "LOAD_UNLOAD_WORKS","SECOND_DRIVER"];
    var newStatus = document.getElementById('driver_status');
    var newStatusVal = newStatus.value;
    var xmlReq = new XMLHttpRequest();
    xmlReq.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var resp = JSON.parse(this.responseText);
            if (resp === 'DRIVER_STATUS_UPDATED'){
                var resStr = "<select class=\"form-control\" id=\"driver_status\" name=\"driverStatus\" style=\"width: 400px;\">\n" +
                    "\t\t\t\t\t\t\t\t\t<option style=\"color: #0ed61f\">"+newStatusVal+"</option>\n";
                for(var i = 0; i < statusesArr.length; i++){
                    if (statusesArr[i]!==newStatusVal){
                        resStr += "\t\t\t\t\t\t\t\t\t\t\t<option>"+statusesArr[i]+"</option>\n";
                    }
                }
                resStr += "\t\t\t\t\t\t\t</select>";
                document.getElementById('driverStatusSelect').innerHTML = resStr;
                alert("Your status updated successfully!")
            }
            else {
                alert("Driver status can not be updated, reason: " + resp);
            }
        }
    };
    var url = contextName + "/updatedriverstatus?driverId=" + driverId + "&newStatus=" + newStatusVal;
    xmlReq.open("POST", url, true);
    xmlReq.send();
}




function updateOrderStatus(orderId) {
    var statusesArr = ["NOT_PREPARED", "PREPARED","EXECUTING", "EXECUTED"];
    var newStatus = document.getElementById('order_status');
    var newStatusVal = newStatus.value;
    var xmlReq = new XMLHttpRequest();
    xmlReq.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var resp = JSON.parse(this.responseText);
            if (resp.status === 'ORDER_STATUS_UPDATED'){

                var resStr = "<select class=\"form-control\" id=\"order_status\" name=\"orderStatus\" style=\"width: 400px\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t<option style=\"color: #0ed61f\">"+newStatusVal+"</option>";
                for(var j = 0; j < statusesArr.length; j++){
                    if (statusesArr[j]!==newStatusVal){
                        resStr += "\t\t\t\t\t\t\t\t\t\t\t<option>"+statusesArr[j]+"</option>\n";
                    }
                }
                resStr += "\t\t\t\t\t\t\t</select>";
                document.getElementById('orderStatusSelect').innerHTML = resStr;
                alert("Order status updated successfully!");
                if (resp.refresh === 'TRUE'){
                    location = location;
                }
            }
            else {
                alert("Order status can not be updated, reason: " + resp);
            }
        }
    };
    var url = contextName + "/updateorderstatus?orderId=" + orderId + "&newStatus=" + newStatusVal;
    xmlReq.open("POST", url, true);
    xmlReq.send();
}




function updateCargoStatus(cargoId) {
    var statusesArr = ["PREPARED","LOADED", "SHIPPING", "DELIVERED"];
    var cargoSelectId = "cargoStatusSelect_" + cargoId;
    var newStatus = document.getElementById(cargoSelectId);
    var newStatusVal = newStatus.value;
    var xmlReq = new XMLHttpRequest();
    xmlReq.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            var resp = JSON.parse(this.responseText);
            if (resp === 'CARGO_STATUS_UPDATED'){
                var resStr = "<select class=\"form-control\"  name=\"cargoStatus\" style=\"width: 200px\" id=\"cargoStatusSelect_"+cargoId+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<option style=\"color: #0ed61f\">"+newStatusVal+"</option>\n";
                for(var i=0; i < statusesArr.length; i++){
                    if (statusesArr[i]!==newStatusVal){
                        resStr += "<option>"+statusesArr[i]+"</option>";
                    }
                }
                resStr += "</select>";
                var tdId = "cargoStatusTd_" + cargoId;
                document.getElementById(tdId).innerHTML = resStr;
                alert("Cargo status updated successfully!");
            }
            else {
                alert("Cargo status can not be updated, reason: " + resp);
            }
        }
    };
    var url = contextName + "/updatecargostatus?cargoId=" + cargoId + "&newStatus=" + newStatusVal;
    xmlReq.open("POST", url, true);
    xmlReq.send();
}
