function init() {
    Tabletop.init( { key: ‘https://docs.google.com/spreadsheets/d/1bm-kxa0Byg-wsrpNH3Nt1G46WO6Y5Gto8078YlNDMZM/pubhtml',
        callback: function(data, tabletop) {
            console.log(data)
        },
    simpleSheet: true } )
}
window.addEventListener(‘DOMContentLoaded’, init)
