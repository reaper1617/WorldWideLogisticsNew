let contextName = "/worldwidelogistics";
function myMap() {
    const mapProp = {
        center: new google.maps.LatLng(59.939095, 30.315868),
        zoom: 5,
    };
    const map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
    const req = new XMLHttpRequest();
    const routeColorsArr = ["red", "green", "blue", "yellow"];
    req.onreadystatechange = function () {

        if (this.readyState === 4 && this.status === 200){
            const ordersArr = JSON.parse(this.responseText);
            for(let i=0; i < ordersArr.length; i++){
                const orderItem = ordersArr[i];
                const routeArr = orderItem.route;
                const routeSize = routeArr.length;
                const routePointOrigin = routeArr[0];
                const routePointDestination = routeArr[routeSize - 1];
                const wayPoints = [];
                for(let m=1; m < routeSize-1; m++){
                    wayPoints.push({
                        location: routeArr[m],
                        stopover: false
                    });
                }
                const request = {
                    origin: routePointOrigin,
                    destination: routePointDestination,
                    travelMode: google.maps.DirectionsTravelMode.DRIVING,
                    waypoints: wayPoints
                };
                const directionsService = new google.maps.DirectionsService();
                const routeColor = routeColorsArr[getRndInteger(0,routeColorsArr.length-1)];
                const directionsDisplay = new google.maps.DirectionsRenderer(
                    {
                        suppressMarkers: true,
                        polylineOptions: {
                            strokeColor: routeColor
                        }
                    }
                );
                directionsService.route(request, function(response, status) {
                    if (status === google.maps.DirectionsStatus.OK) {
                        directionsDisplay.setDirections(response);
                    }
                    else {
                        alert("NOT OK! " + response);
                    }
                });
                directionsDisplay.setMap(map);
                const currentCityName = orderItem.currentCity;
                for(let k = 0; k < routeArr.length; k++){
                    const cityPoint = routeArr[k];
                    if (cityPoint === currentCityName){
                        let myCenter = getLatlng(cityPoint);
                        let marker = new google.maps.Marker({position:myCenter});
                        marker.setMap(map);
                        let contentString = "Order:" + orderItem.description + "; "
                            + "personal number: " + orderItem.personalNumber + "; "
                            + "date: " + orderItem.date + "; "
                            + "status: " + orderItem.status + "; "
                            + "truck: " + orderItem.assignedTruckRegistrationNumber + "; ";
                        contentString += "drivers: ";
                        if (typeof orderItem.assignedDrivers === 'undefined') {
                            contentString+="no drivers."
                        }
                        else{
                            for (let j = 0; j < orderItem.assignedDrivers.length; j++) {
                                const driver = orderItem.assignedDrivers[j];
                                contentString += driver + "; ";
                            }
                        }
                        let infowindow = new google.maps.InfoWindow({
                                content: contentString
                            }
                        );
                        google.maps.event.addListener(marker, 'click', function () {
                            infowindow.open(map,marker);
                        });
                        google.maps.event.addListener(marker, 'mouseout', function () {
                            infowindow.close();
                        });
                    }
                    else{
                        let myCenter = getLatlng(cityPoint);//
                        let marker = new google.maps.Marker({position: myCenter});
                        marker.setMap(map);
                        let contentString = "Order:" + orderItem.description + "; "
                            + "personal number: " + orderItem.personalNumber + "; "
                            + "route point " + (Number)(k + 1);
                        let infowindow = new google.maps.InfoWindow({
                                content: contentString
                            }
                        );
                        google.maps.event.addListener(marker, 'click', function () {
                            infowindow.open(map,marker);
                        });
                        google.maps.event.addListener(marker, 'mouseout', function () {
                            infowindow.close();
                        })
                    }
                }



            }
        }
    };
    const size = 10;
    const reqURL = contextName +  '/gettoporders?size=' + size;
    req.open("GET", reqURL, true);
    req.send();
}

function getRndInteger(min, max) {
    return Math.floor(Math.random() * (max - min + 1) ) + min;
}



function getLatlng(cityName) {
    let globalLatLng;
    const xmlreq = new XMLHttpRequest();
    xmlreq.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200){
            const res = JSON.parse(this.responseText);
            if (res.status === "OK"){
                const resArrFromGoogle = res.results;
                const lat = resArrFromGoogle[0].geometry.location.lat;
                const lng = resArrFromGoogle[0].geometry.location.lng;
                globalLatLng = new google.maps.LatLng(lat, lng);
            }
        }
    }
    const requestedUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + cityName + "&key=AIzaSyD-kLRYC4_d1Z7zDgGJj_DplCfC7a_Mr3k&language=en";
    xmlreq.open('GET',requestedUrl, false);
    xmlreq.send();
    return globalLatLng;
}

