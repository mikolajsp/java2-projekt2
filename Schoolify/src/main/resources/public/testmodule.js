import autocomplete from 'autocompleter';
var autocomplete = require('autocompleter');

var countries = [
    { label: 'United Kingdom', value: 'UK' },
    { label: 'United States', value: 'US' }
];

var input = document.getElementById("itown");

autocomplete({
    input: input,
    fetch: function(text, update) {
        text = text.toLowerCase();
        var suggestions = countries.filter(n => n.label.toLowerCase().startsWith(text))
        update(suggestions);
    },
    onSelect: function(item) {
        input.value = item.label;
    }
});