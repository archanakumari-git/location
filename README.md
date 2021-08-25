# location

**Add new location** 
1. Open rest client and type url `http://localhost:8080/locations`
2. Select body content type as `application-json`
3. Provide body in given format :
 
`{
   "city": "bangalore",
   "zipCode": 560038,
   "state": "Karnataka",
   "country" : "India"
   } `
4. Select method as post and click send , location created successfully

**Get all locations** 
1. Open rest client and type url `http://localhost:8080/locations`
2. Select method as get and click send

**Get location by city**
1. Open rest client and type url `http://localhost:8080/locations/<city_name>`
where city_name is name of the city 
2. Select method as get and click send

**Get location by zip code**
1. Open rest client and type url `http://localhost:8080/locations/zipcode/<code>`
   where code is zipcode of the city
2. Select method as get and click send

**Delete location by city**
1. Open rest client and type url `http://localhost:8080/locations/<city_name>`
   where city_name is name of the city
2. Select method as delete and click send