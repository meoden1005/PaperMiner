/**
 * 
 */

var historyList;
var chosenList;
// var userId;

jQuery(document).ready(function(){
	// alert('hi');
	historyList = jQuery.jStorage.index();
	document.getElementById('optionList').appendChild(makeUL(historyList));
	// makeUL(historyList);
	
	// userId = m_user.id;
	
	jQuery('#optionList').change(function () {
		  var str = '';
		  chosenList = new Array();
		  
		  jQuery('#optionList option:selected').each(function (index) {
		        str += jQuery(this).text() + ' ';
		        // chosenList[index] = jQuery(this).text();
		        chosenList[index] = queryPmQueryByDesc(jQuery(this).text()); 
		      });
		  jQuery('#textSelected').text(str);
		})
		.trigger('change');
	
});

function queryPmQueryByDesc(desc) {
	var result = new Object();
	var array = jQuery.jStorage.index();
	
	for(var i = 0; i < array.length; i++) {
		var item = jQuery.jStorage.get(array[i]);
		if (item.desc == desc) {
			return item;
		}
	}
}

function makeUL(array) {
    // Create the list element:
    var list = document.createElement('select');
    list.setAttribute('id', 'optionList');
    list.setAttribute('multiple', 'multiple');

    for(var i = 0; i < array.length; i++) {
        // Create the list item:
        var item = document.createElement('option');
        
        
        // Set its contents:
        // item.appendChild(document.createTextNode(array[i] + '-' +
		// jQuery.jStorage.get(array[i])));
        // retrive object
        var currentItem = jQuery.jStorage.get(array[i]);
        
        item.appendChild(document.createTextNode(currentItem.desc));
        
        // Add it to the list:
        list.appendChild(item);
    }

    // Finally, return the constructed list:
    return list;
}

function saveHistory() {
	
	for(var i = 0; i < chosenList.length; i++) {
		//ws/api/saveQuery/' + encodeURIComponent(chosenList.join()) + '/userId/' + m_user.id
		var request = jQuery.ajax({
			url:PM_URI + '/ws/api/saveQuery/' + 'userId/' + m_user.id + '/queryDescr/' + encodeURIComponent(chosenList[i].desc) + '/queryType/' + 's' + '/queryTotal/' + chosenList[i].totalresult + '/query/' + encodeURIComponent(chosenList[i].query),
			type: 'GET'
		});
	}	
}
