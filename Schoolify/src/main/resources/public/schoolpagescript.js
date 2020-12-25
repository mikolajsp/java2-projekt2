function getQueryVariable(variable)
{
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}

function getschoolinfo(url){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    var content = JSON.parse(xmlHttp.responseText);
    return content;
}

function createDetailedSchoolDescription(school) {
    return "<h4 class=\"mb-3\">"+ school.name +
        "</h4>" + "<p>"+
        "Typ: " + school.type + "<br>" +
        "Województwo: " + school.voivodeship + "<br>" +
        "Powiat: " + school.county + "<br>" +
        "Gmina: " + school.community + "<br>" +
        "Liczba oddziałów: " + school.branches + "<br>" +
        "Liczba uczniów: " + school.students + "<br>" +
        "Email: " + school.email + "<br>" +
        "Telefon: " + school.phoneNumber + "<br>" +
        "Strona internetowa: <a href=\"https://" + school.website + "\">" + school.website + "</a>" + "<br>" +
        "Adres: " + school.street + " " + school.houseNumber + ", " + school.town + "<br>"+
        "</p>"
    }

function main(){

    schoolid = getQueryVariable("schoolid")
    url = "http://localhost:8080/school/id/" + schoolid;
    console.log(url)
    school = getschoolinfo(url)

    document.getElementById("info").innerHTML = createDetailedSchoolDescription(school);

}