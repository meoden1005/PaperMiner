$(function() {
	
	
	$('#legend-key li').off();
	
	$('#legend-key li').on("click", function() {
		var mtype = _getCheckedMapButton();
		if (mtype !== PUBLISHER_MARKER) {
			var toggleLast = $('#legend-key li:last-child').hasClass("off");
			$('#legend-key li').removeClass("off");
			var keyLength = $("#legend-key li").length;
			var keyListNumber = $("#legend-key li").index(this);
			var i = keyLength - keyListNumber;
			var currentIndex = keyLength - (keyListNumber + 1);
			var currentItem;
			
			// Toggle items within the key list
			if (keyListNumber == keyLength - 1)
			{
				toggleLast ? $(this).removeClass("off") : $(this).addClass("off");
			}	else {
				while (i > 0) 
				{
					currentItem = (keyLength - i);
					$('#legend-key li:eq('+ currentItem +')').addClass("off");
					i--;
				}
			}
			
			currentIndexValue = currentIndex;
			ToggleBasedOnCheckedKeyIndex();
		}
	});
});


/**
 * SEN
 * Toggles map markers based upon key index max values.
 */
function ToggleBasedOnCheckedKeyIndex ()
{
	if (currentIndexValue === null)
	{
		currentIndexValue = -1;
	}
	
  if (m_resultSet !== null) {
	
		for (var i = 0; i < m_resultSet.length; i++) {
			if (m_resultSet[i].marker !== null) {
				m_resultSet[i].marker.setVisible(false);     
			}
		}
	
		for (var locnId in m_locationsCache) {
			if (m_locationsCache[locnId].marker != null) {
				var markerImageName = m_locationsCache[locnId].marker.icon;
				var splitFirstHalf = markerImageName.split("/");
				var fileName = splitFirstHalf[1];
				var splitSecondHalf = fileName.split(".");
				var imageName = splitSecondHalf[0];
				var imageNumber = imageName.slice(3);
				var number = parseInt(imageNumber);
				var indexNumber = parseInt(currentIndexValue);			
				var showMarker = (number > indexNumber);

				m_locationsCache[locnId].marker.setVisible(showMarker);
			}
		}

	}
}