//todo: make one func with input parameter selectId !

let contextName = "/worldwidelogistics";

function showRoute() {
    var selectedValuesArr = document.getElementById('add_cargos');
    var reqUrl = contextName + "/getorderroute?";
    for(var i = 0; i < selectedValuesArr.options.length; i++){
        opt = selectedValuesArr.options[i];
        if(opt.selected === true){
            reqUrl+="selectedVal="+opt.value+"&";
        }
    }
    reqUrl += "truck=0";

    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4){
            var arr = JSON.parse(this.responseText);
            var routeString = "";
            for(var i = 0; i < arr.length; i++){
                var city = arr[i];
                routeString += city.name + " -> ";
            }
            routeString = routeString.substring(0, routeString.length - 4);
            document.getElementById('currentRoute').innerHTML = routeString;
        }
    };
    req.open("GET", reqUrl, true);
    req.send();
}

function showRouteOnTruckAssignPage() {
    var selectedValuesArr = document.getElementById('add_cargos');
    var reqUrl = contextName + "/getorderroute?";
    for(var i = 0; i < selectedValuesArr.options.length; i++){
        opt = selectedValuesArr.options[i];
        if(opt.selected === true){
            reqUrl+="selectedVal="+opt.value+"&";
        }
    }

    var selectedTruckEl = document.getElementById('add_truck');
    var truck = null;
    for(var j = 0; j < selectedTruckEl.length; j++){
        opt = selectedTruckEl.options[j];
        if(opt.selected === true){
            truck = opt.value;
        }
    }
    reqUrl +="truck="+truck;

    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4){
            var arr = JSON.parse(this.responseText);
            var routeString = "";
            for(var i = 0; i < arr.length; i++){
                var city = arr[i];
                routeString += city.name + " -> ";
            }
            routeString = routeString.substring(0, routeString.length - 4);
            document.getElementById('currentRoute').innerHTML = routeString;
        }
    };
    req.open("GET", reqUrl, true);
    req.send();
}

function showMessageIfTruckHasDriversHoursOverLimit() {
    var selectedValuesArr = document.getElementById('add_cargos');
    var reqUrl = contextName + "/getdrivershoursoverlimit?";
    for(var i = 0; i < selectedValuesArr.options.length; i++){
        opt = selectedValuesArr.options[i];
        if(opt.selected === true){
            reqUrl+="selectedVal="+opt.value+"&";
        }
    }

    var selectedTruckEl = document.getElementById('add_truck');
    var truck = null;
    for(var j = 0; j < selectedTruckEl.length; j++){
        opt = selectedTruckEl.options[j];
        if(opt.selected === true){
            truck = opt.value;
        }
    }
    reqUrl +="truck="+truck;

    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        // alert("status:" + this.status + " state = " + this.readyState);
        if (this.status === 200 && this.readyState === 4){
            // alert("resp:" + this.responseText);
            var arr = JSON.parse(this.responseText);
            if (typeof arr.result === 'undefined' ){
                var message = 'Warning: chosen truck has driver(s) whose can not execute this order:\n';
                for(var i = 0; i < arr.length; i++){
                    message += "driver:"
                        + arr[i].firstName + " "
                        + arr[i].middleName + " "
                        + arr[i].lastName + " "
                        + arr[i].personalNumber
                        + " hours worked:" + arr[i].hoursWorked +"\n";
                }
                message += "\n" + "If you want to assign this truck you need to unassign unsuitable driver(s) first." + "\n";
                alert(message);
            }
            else{
                if (arr.driversInTruck === 'empty'){
                    var emptyDriversMessage = "\n" + "Warning: this truck is fit but has no assigned drivers. \n If you want to assign this truck you need assign suitable driver(s) first." + "\n";
                    alert(emptyDriversMessage);
                }
            }
        }
    };
    req.open("GET", reqUrl, true);
    req.send();
}

function makeRedIfTruckHasDriversHoursOverLimit() {
    var selectedValuesArr = document.getElementById('add_cargos');
    var reqUrl = contextName + "/getdrivershoursoverlimit?";
    for(var i = 0; i < selectedValuesArr.options.length; i++){
        opt = selectedValuesArr.options[i];
        if(opt.selected === true){
            reqUrl+="selectedVal="+opt.value+"&";
        }
    }

    var selectedTruckEl = document.getElementById('add_truck');
    var truck = null;
    for(var j = 0; j < selectedTruckEl.length; j++){
        opt = selectedTruckEl.options[j];
        if(opt.selected === true){
            truck = opt.value;
        }
    }
    reqUrl +="truck="+truck;

    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4){
            var arr = JSON.parse(this.responseText);
            if (typeof arr.result === 'undefined' ){
                let truckOptionId = "truck"+truck;
                let truckOption = document.getElementById(truckOptionId).style.color="#b21f2d";
            }
        }
    };
    req.open("GET", reqUrl, true);
    req.send();
}