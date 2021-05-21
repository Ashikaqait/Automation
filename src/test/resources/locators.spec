Element                 Locator Type         Locator
search_field:           css:                 #LocationSearch_input
dropdown_city:          xpath:               //button[contains(text(),"${text}")][1]
weather_page:           xpath:               //h1[contains(text(),"${text}")]
current_temp:           css:                 span.CurrentConditions--tempValue--3KcTQ
google_icon:            xpath:               //*[contains(@content-desc,"Continue with Google")]
twitter_icon:           xpath:               //*[contains(@content-desc,"AuthoriseWithTwitter_593")]
user:                   xpath:                //*[@resource-id="username_or_email"]
password:               xpath:                //*[@resource-id="password"]
submitbtn:              xpath:                //*[@resource-id="allow"]
confirmtxt:             xpath:                //*[contains(@text,"Check your email")]
authorisetext:          xpath:                //*[contains(@class,"")][@text()="$text"]
