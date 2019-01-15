$(document).ready(function () {
	
	load_weather_data();

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function populateWeatherTable(weatherData) {
	var weatherTable = document.getElementById("weatherTable");
	var currentIndex = 1;
	
	weatherData.forEach(function(eachWeatherEntry) {
		var row = weatherTable.insertRow(currentIndex);
		currentIndex = currentIndex + 1;
	
		// Insert 
		var cell1 = row.insertCell(0); // Station
		var cell2 = row.insertCell(1); // Province
		var cell3 = row.insertCell(2); // Date
		var cell4 = row.insertCell(3); // Mean_Temp
		var cell5 = row.insertCell(3); // Highest_Monthly_Maxi_Temp
		var cell6 = row.insertCell(3); // Lowest_Monthly_Min_Temp
		
		// Add details text to the new cells
		cell1.innerHTML = eachWeatherEntry.station_name;
		cell2.innerHTML = eachWeatherEntry.date;
		cell3.innerHTML = eachWeatherEntry.province;
		cell4.innerHTML = eachWeatherEntry.meanTemp;
		cell5.innerHTML = eachWeatherEntry.highest_Monthly_Max_Temp;
		cell6.innerHTML = eachWeatherEntry.lowestMonthly_Min_Temp;
	})
}


function load_weather_data() {

   $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/weatherData",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            //var json = "<h4>Ajax Response</h4><pre>"
            //    + JSON.stringify(data, null, 4) + "</pre>";
            //$('#feedback').html(json);

            console.log("SUCCESS : ", data);
            populateWeatherTable(data);

        },
        error: function (e) {


            console.log("ERROR : ", e);


        }
    });
}

function fire_ajax_submit() {

    var search = {}
    search["username"] = $("#username").val();
    //search["email"] = $("#email").val();

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/search",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}