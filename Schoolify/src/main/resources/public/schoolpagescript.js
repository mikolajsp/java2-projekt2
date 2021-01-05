var DOMAIN = "http://localhost:8080";


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



    var res = "<h4 class=\"mb-3\">"+ school.name +
        "</h4>" + "<p>"+
        "<b>Typ</b>:  " + school.type + "<br>" +
        "<b>Województwo</b>:  " + school.voivodeship + "<br>";
        if(school.county != "-"){
            res += "<b>Powiat</b>:  " + school.county + "<br>";
        }
        if(school.community != "-"){
            res += "<b>Gmina</b>:  " + school.community + "<br>";

        }
        if(school.branches != 0){
            res += "<b>Liczba oddziałów</b>:  " + school.branches + "<br>";
        }
        if(school.students != 0){
            res += "<b>Liczba uczniów</b>:  " + school.students + "<br>";
        }
    res +=  "<b>Email:</b>  " + school.email + "<br>" +
        "<b>Telefon:</b>  " + school.phoneNumber + "<br>" +
        "<b>Strona internetowa:</b>  <a href=\"https://" + school.website + "\">" + school.website + "</a>" + "<br>" +
        "<b>Adres:</b>  " + school.street + " " + school.houseNumber + ", " + school.town + "<br>"+
        "</p>";

    return res;

    }


function getComments(id){
    var url = DOMAIN + "/comment/pid/" + id;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    var content = JSON.parse(xmlHttp.responseText);
    return content;
}

function sendComment(){
    var content = document.getElementById("comment").value 
    var user = document.getElementById("username").value 
    var rate = getValueOfStars(1);
    var schoolid = parseInt(getQueryVariable("schoolid"))
    var xhr = new XMLHttpRequest();
    var params = "?schoolId="+schoolid+"&content=" + content + "&username=" + user + "&rate=" + rate;
    xhr.open("POST", DOMAIN+"/comment/add"+params, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(null);
}


function getVotingDiv(com){
    var idComment = com.id;
    
    var voting = `<div class="column"> <div class="outer" id="vote${idComment}">
    <div class="inner"><button id="up${idComment}" style="border: 0; background: transparent" onclick="upVoteButton(${idComment})"><img src="upVote.png" width="20" height="20" alt="submit" /></button></div>
    <div class="inner"><button id="down${idComment}" style="border: 0; background: transparent" onclick="downVoteButton(${idComment})"><img src="downVote.png" width="20" height="20" alt="submit" /></button></div>
  </div></div>`;
  return voting;
}

function upVoteButton(idc){
    document.getElementById(`up${idc}`).disabled = true;
    var url = DOMAIN + "/comment/upvote/" + idc;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(null);

}

function downVoteButton(idc){
    document.getElementById(`down${idc}`).disabled = true;
    var url = DOMAIN + "/comment/downvote/" + idc;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(null);
}


function generateCommentText(comment){
    var today = new Date();
    var posted = new Date(comment.date);
    var mili = today.getTime() - posted.getTime();
    var daysDiff = Math.floor(mili/(1000*60*60*24));
    var inline;
    var val = comment.upVotes-comment.downVotes;
    if(val > 0){
        inline = `+${val} punktów`;
    }else{
        inline = `${val} punktów`;
    }
    var res2 = `<div class="row">
  <div class="column left">` + 
    getVotingDiv(comment) + 
  `</div>
  <div class="column middle">` + 
      `<div class="row">` + 
          `<pre>` + `<b style="font-size:15px;">` + comment.user + 
          `</b>` + `<span style="font-size:10px;">   ` + inline + `</span>` + `</pre>` +
        `</div>` +
    `<div class="row">` + 
      `<pre style="font-size:10px;">Opublikowano : ` + daysDiff + ` dni temu.</pre>` + 
    `</div>` + 
    `<div class="row">` + 
      `<pre>` + comment.content + `</pre>`+
    `</div>`+
  `</div>`+
  `<div class="column right">` + 
     getNBlue(comment.rate) + 
  `</div></div>`;
    return res2;

}

function displayComments(commentArray){
    var sum = 0;
    for(var comment in commentArray){
        var com = commentArray[comment];
        sum += com.rate;
        var newElement = document.createElement('div');
        newElement.id = commentArray[comment].id;
        newElement.className = "comment";
        newElement.innerHTML = generateCommentText(com);
        commentArray[comment].schoolId + commentArray[comment].content;
        document.getElementById("comments").appendChild(newElement);
    }
    var avg;
    if(commentArray.length > 0){
            avg = sum/commentArray.length;
    }else{
        avg = -1;
    }
    return avg
}

function putAvg(avg){

    var txt = "<br> Średnia ocena: <br>" +  getNBlue(Math.round(avg));
    var nel = document.createElement('p');
    nel.innerHTML = txt;
    document.getElementById("avg").appendChild(nel);
}


function main(){
  //   window.navigator.geolocation
  // .getCurrentPosition(console.log, console.log); snippet that gets userlocalization
    var schoolid = getQueryVariable("schoolid");
    console.log(schoolid);
    var url = DOMAIN + "/school/id/" + schoolid;
    var school = getschoolinfo(url);
    console.log(school);
    document.getElementById("info").innerHTML = createDetailedSchoolDescription(school.school);
    var comments = getComments(schoolid);
    createRadar(school.school.name, school.assesment,"myChart");
    var avg = displayComments(comments.comments);
    putAvg(avg);
    
}

function launchModal(){
    $('#surveyModal').modal('show')
}

function hideModal(){
    $('#surveyModal').modal('hide')
}


function getScore(){

$("label").click(function(){
  $(this).parent().find("label").css({"background-color": "#D8D8D8"});
  $(this).css({"background-color": "#0000FF"});
  $(this).nextAll().css({"background-color": "#0000FF"});
});
}

function getValueOfStars(field_number){
    var f = `field${field_number}_star`;
    for(var i = 1; i <= 5; i++){
        if(document.getElementById(f+i).checked){
            return i;
        }
    }
    return -1;
}

function sendSurvey(){
    var schoolid = getQueryVariable("schoolid");
    var qual = getValueOfStars(2);
    var frien = getValueOfStars(3);
    var pric= getValueOfStars(4);
    var intr= getValueOfStars(5);
    var comm= getValueOfStars(6);
    if(qual == -1 || frien == -1 || pric == -1 || intr == -1 || comm == -1){
        alert("Zaznacz wszystkie kategorie!");
    }else{
        console.log(qual);
        console.log(frien);
        console.log(pric);
        console.log(intr);
        console.log(comm);
        var xhr = new XMLHttpRequest();
        var params = "?id="+schoolid+"&educational=" + qual +"&friendliness=" + frien +"&lowPrice=" + pric +"&intrests=" + intr +"&commute=" + comm
        xhr.open("POST", DOMAIN+"/school/survey"+params, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(null);
        hideModal();
    }
   
}