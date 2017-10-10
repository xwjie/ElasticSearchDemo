import elasticsearch from 'elasticsearch';

var client = new elasticsearch.Client({
    host: 'localhost:9200',
    log: 'trace'
});

let ela = {};

ela.searchArt = function (key, page) {
    return client.search({
        index: 'article',
        type: 'article',
        body: {
            size: page.pageSize,
            from: page.pageSize * (page.currentPage - 1),
            query: {
                match_phrase: {
                    body: key
                }
            },
            _source: {
                //includes: [
                //    '*.count',
                //    'meta.*'
                //],
                excludes: ['body']
            },
            highlight: {
                fields: {
                    body: {},
                    title: {}
                }
            }
        }
    }).then(function (resp) {
        return resp.hits;
    }, function (err) {
        console.trace(err.message);
    });
};

export default ela;