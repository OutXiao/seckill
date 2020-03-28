$(function()
{
	
	/*
	define a new language named "custom"
	*/

	$.dateRangePickerLanguages['custom'] = 
	{
		'selected': 'Choosed:',
		'days': 'Days',
		'apply': 'Close',
		'week-1' : 'Mon',
		'week-2' : 'Tue',
		'week-3' : 'Wed',
		'week-4' : 'Thu',
		'week-5' : 'Fri',
		'week-6' : 'Sat',
		'week-7' : 'Sun',
		'month-name': ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
		'shortcuts' : 'Shortcuts',
		'past': 'Past',
		'7days' : '7days',
		'14days' : '14days',
		'30days' : '30days',
		'previous' : 'Previous',
		'prev-week' : 'Week',
		'prev-month' : 'Month',
		'prev-quarter' : 'Quarter',
		'prev-year' : 'Year',
		'less-than' : 'Date range should longer than %d days',
		'more-than' : 'Date range should less than %d days',
		'default-more' : 'Please select a date range longer than %d days',
		'default-less' : 'Please select a date range less than %d days',
		'default-range' : 'Please select a date range between %d and %d days',
		'default-default': 'This is costom language'
	};

	$('#add-date-range').dateRangePicker(
	{
		startOfWeek: 'monday',
    	separator : '~',
    	format: 'YYYY-MM-DD HH:mm',
    	autoClose: false,
		time: {
			enabled: true
		}
	});

    $('#edit-date-range').dateRangePicker({
            startOfWeek: 'monday',
            separator : '~',
            format: 'YYYY-MM-DD HH:mm',
            autoClose: false,
            time: {
                enabled: true
            }
        });

});
