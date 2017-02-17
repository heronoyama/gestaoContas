requirejs.config({
    baseUrl: 'js/vendor',
    shim: { 'bootstrap' : {'deps':['jquery']}},
    paths: {
        app: '../app',
        text: 'requireText',
        bootstrap: 'bootstrap.min',
        jquery: 'jquery-3.1.0.min',
        metsMenu: 'jquery.metisMenu',
        knockout: 'knockout-3.3.0',
        sammy: 'sammy',
        chart: 'Chart',
        utils: '../utils',
        components: '../app/Components',
        navigator:'../navigator',
        gadgets : '../app/Gadgets',
        gateway: '../Gateway',
        models: '../app/Models',
        controllers: '../app/Controllers'
    }
});