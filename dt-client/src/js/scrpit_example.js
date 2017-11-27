
function loadData() {

    var $body = $('body');
    var $wikiElem = $('#wikipedia-links');
    var $nytHeaderElem = $('#nytimes-header');
    var $nytElem = $('#nytimes-articles');
    var $greeting = $('#greeting');

    // clear out old data before new request
    $wikiElem.text("");
    $nytElem.text("");

    // load streetview
    var streetStr = $('#street').val();
    var cityStr = $('#city').val();
    var address = streetStr + ', ' + cityStr;
    var streetviewUrl = 'http://maps.googleapis.com/maps/api/streetview?size=600x300&location=' + address + '';
    $body.append('<img class="bgimg" src=' + streetviewUrl + '></img>');

    // YOUR CODE GOES HERE!
    var nytUrl = 'https://api.nytimes.com/svc/search/v2/articlesearch.json';
    nytUrl += '?' + $.param({
      'q': cityStr,
      'api-key': '59696588001f4e149018839823f073f7',
      'sort': 'newest'
    });
    $.getJSON(nytUrl, function(data){
      $nytHeaderElem.text('New York Times Articles About '+ cityStr);
      articles = data.response.docs;
      for(var i=0; i<articles.length; i++){
        var article = articles[i];
        $nytElem.append('<li class="article">' +
                      '<a href="' + article.web_url + '">' + article.headline.main + '</a>' +
                    '<p>' + article.snippet + '</p>' + '</li>' );
      }
    }).fail(function(e){
      $nytHeaderElem.text('New York Times Article Could Not Be Loaded');
    });

    var wikiUrl = 'http://en.wikipedia.org/w/api.php?action=opensearch&search=' + cityStr
    + '&format=json&callback=wikiCallback';
    var wikiRequestTimeout = setTimeout(function (){
      $wikiElem.text("failed to get wikipedia resource");
    }, 5000);
    $.ajax({
      url: wikiUrl,
      // method: 'GET',
      dataType: 'jsonp',
      // jsonp: "callback",
      success: function(response){
        var articleList = response[1];

        for(var i=0; i<articleList.length; i++){
          var articleStr = articleList[i];
          var url = 'http://en.wikipedia.org/wiki/' + articleStr;
          $wikiElem.append('<li><a href="' + url + '">' + articleStr + '</a></li>');
        }

        clearTimeout(wikiRequestTimeout);
      }
    });

    return false;
};

$('#form-container').submit(loadData);
