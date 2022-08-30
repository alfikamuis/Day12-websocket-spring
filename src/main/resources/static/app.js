var stompClient = null;
let url= 'http://localhost:8080'

//V2
function connectv2(userName){
    console.log("connecting to chat")
    let socket = new SockJS(url+'/chat')
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame)
    {
        console.log("connected: " +frame);
        stompClient.subscribe("/topic/messages" + userName , function (greeting) { //from message controller
                    showGreeting(JSON.parse(greeting.body));
                    console.log(JSON.parse(greeting.body)); //debug
                });
    });
}

function sendMessage(from,text){
    stompClient.send("/app/stomp-endpoint/"+selectedUser,{},JSON.stringify({
        fromlogin: from,
        message: text
    }));
}

function registration(){
    let userName = document.getElementById("name").value;
    $.get(url+"/registration/"+userName ,function (response) {
    }).fail(function (error){
        if(error.status === 400){
            alert("login already busy!")
        }    
    })
}

function fetchAll(){
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '            </li></a>';
        }
        $('#online').html(usersTemplateHTML);
    });
}

//============================================================

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (greeting) { //from message controller
            showGreeting(JSON.parse(greeting.body).messages);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/sayHi", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});