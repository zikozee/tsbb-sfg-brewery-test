# tsbb-sfg-brewery-test
[![CircleCI](https://circleci.com/gh/zikozee/tsbb-sfg-brewery-test/tree/main.svg?style=svg)](https://circleci.com/gh/zikozee/tsbb-sfg-brewery-test/tree/main)

... top right > Project Settings > Status badges


### Components
- Json path  https://github.com/json-path/JsonPath
- custom message converter
- converting date to proper format using object mapper

- ```text
   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.springframework.data.domain.Pageable]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `org.springframework.data.domain.Pageable` (no Creators, like default construct, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information
   at [Source: (PushbackInputStream); line: 1, column: 306] (through reference chain: com.zikozee.brewery.web.model.BeerOrderPagedList["pageable"])
  ```
- test **PageImpl** (see com.zikozee.brewery.web.model.BeerPagedList, BeerControllerIT)
- test **PageImpl** (see com.zikozee.brewery.web.model.BeerOrderPagedList, BeerOrderControllerIT)