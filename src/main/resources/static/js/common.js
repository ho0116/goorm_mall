// 소수점을 제거하고 정수로 표시하는 함수
function removeDecimalAndDisplayPrice(className) {
    // 클래스 이름으로 요소를 가져옴
    var totalPriceElements = document.querySelectorAll('.' + className);
    
    // 각 요소에 대해 반복 처리
    totalPriceElements.forEach(function(element) {
        // 현재 가격 값 (문자열로 되어 있음)
        var priceText = element.textContent;
        
        // 문자열을 숫자로 변환 후, 소수점을 제거
        var price = Math.floor(parseFloat(priceText));
        
        // 변환된 값을 다시 요소에 설정
        element.textContent = price;
    });
}

// 문서가 로드된 후, 특정 요소의 소수점을 제거하는 함수를 호출
document.addEventListener('DOMContentLoaded', function () {
    removeDecimalAndDisplayPrice('totalPrice');
});
