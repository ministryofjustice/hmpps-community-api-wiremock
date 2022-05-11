# hmpps-community-api-wiremock

The purpose of this tool is to bridge the gap between data inconsistenties between community-api and prison-api in the DEV environment. For services which require a delius case which corresponds to a matching nomis case, this tool can be used to mock out the community-api.

This tool will simulate the "team" structure of the probation staff, and will return staff and offender details using the same contracts and schemas as they would be returned from community-api. It integrates with prison-api directly, reads information, and produces a matching delius record to be returned, using Faker library to mock out some fields: e.g. CRN

