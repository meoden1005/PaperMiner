/**
 * 
 */

var historyList;

jQuery(document).ready(function(){
	alert('hi');
	historyList = jQuery.jStorage.index();
	document.getElementById('optionList').appendChild(makeUL(historyList));
	//makeUL(historyList);
});

function makeUL(array) {
    // Create the list element:
    var list = document.createElement('select');
    list.setAttribute('id', 'optionList');
    list.setAttribute('multiple', 'multiple');

    for(var i = 0; i < array.length; i++) {
        // Create the list item:
        var item = document.createElement('option');
        
        
        // Set its contents:
        item.appendChild(document.createTextNode(array[i] + '-' + jQuery.jStorage.get(array[i])));

        // Add it to the list:
        list.appendChild(item);
    }

    // Finally, return the constructed list:
    return list;
}