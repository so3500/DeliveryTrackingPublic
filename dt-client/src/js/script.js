
function loadData() {

  var invoiceNumber = $('#invoice-number').val()
  if (invoiceNumber.length == 13) {
    var deliveryTrackingUrl = "http://localhost:8080/delivery-tracking";

    var $deliveryTrackingTable = $('#delivery-tracking');
    var deliveryTrackingContents = "";

    var deliveryRequestTimeout = setTimeout(function (){
      // $wikiElem.text("failed to get delivery resource");
      // timeout: 2000ms 이상의 시간 소요 시 에러로 판단
      console.log('No response or error');
    }, 2000);
    let payload = {};
    payload.invoiceNumber = invoiceNumber;
    $.ajax({
      url: deliveryTrackingUrl,
      type: 'GET',
      data: payload,
      dataType: 'json',
      // jsonp: "callback",
      success: function(response){

        // =================
        // 예외 처리 필요, response는 잘 받아왔지만 원하는 결과가 없는 경우는 서버단에서 하지
        // =================
        console.log(response);
        clearTimeout(deliveryRequestTimeout);

        var resultNumber = response.result;
        var result;
        // Server 로부터 받은 JSON 파싱
        switch (resultNumber) {
          case -2:
              result = "내역 없음"
            break;
          case -1:
              result = "잘못된 송장번호";
            break;
          case 0:
              result = "배송 미완료";
            break;
          case 1:
              result = "배송 완료";
            break;
        }
        var invoiceNumber = response.invoiceNumber;
        var deliveryCompany = response.deliveryCompany;
        var date = response.date.split('/');
        var time = response.time.split('/');
        var currentPosition = response.currentPosition.split('/');
        var currentState = response.currentState.split('/');
        var sender = response.sender;
        var receiver = response.receiver;

        // Contents 생성
        deliveryTrackingContents =
        "<tr>" +
        "<td> 송장번호 " + invoiceNumber + "</td>" +
        "<td> 택배사 " + deliveryCompany +"</td>" +
        "<td> 배송결과 "+ result +"</td>" +
        "<td> 보내는 분 "+ sender +"</td>" +
        "<td> 받는 분 "+ receiver +"</td>" +
        "</tr>" +
        "<tr>" +
        "<td> 날짜 </td>" +
        "<td> 시간 </td>" +
        "<td> 현재위치 </td>" +
        "<td> 처리현황 </td>" +
        "</tr>";

        // 결과가 "-" 가 아닐 경우
        var dataLength = date.length;
        if (dataLength != 1) {
            dataLength = dataLength-1;
        }
        for (var i = 0; i < dataLength; i++) {
          deliveryTrackingContents +=
          "<tr>" +
          "<td>" + date[i] + "</td>" +
          "<td>" + time[i] + "</td>" +
          "<td>" + currentPosition[i] + "</td>" +
          "<td>" + currentState[i] + "</td>" +
          "</tr>";
        }

        // Contents 를 DOM에 반영
        $deliveryTrackingTable.append(deliveryTrackingContents);
      },
      error: function(err){
        console.log(err);
      }
    });
    return false; // 기존의 내역을 지우지 않도록 함
  } else {
    // 송장번호가 13자리가 아닐 경우
    alert('우체국택배: 송장번호(13자리)를 올바르게 입력하세요.');
    return false;
  }
};

$('#form-container').submit(loadData);
