import gql from "graphql-tag";

const CREATE_BRAND = gql`
    mutation createBrand($brandDTO: BrandInput!) {
    createBrand(request: $brandDTO) {
        id
        country
        name
    }
}`


const UPDATE_BRAND = gql`
mutation updateBrand($id: ID!, $request: BrandInput!) {
    updateBrand(id: $id, request: $request) {
      name
      country
    }
}`

const DELETE_BRAND = gql`
mutation deleteBrand($id: ID!) {
    deleteBrand(id: $id)
}`

//MODELS

const CREATE_MODEL = gql`
    mutation createModel($modelDTO: ModelInput!) {
    createModel(request: $modelDTO) {
        id
        name
        brandId
    }
}`

const UPDATE_MODEL = gql`
    mutation updateModel($id: ID!, $request: ModelInput!){
        updateModel(id: $id, request: $request){
            name
            brandId
        }
    }
`

const DELETE_MODEL = gql`
mutation deleteModel($id: ID!) {
    deleteModel(id: $id)
}`

export { CREATE_BRAND, UPDATE_BRAND, DELETE_BRAND, CREATE_MODEL, UPDATE_MODEL, DELETE_MODEL };