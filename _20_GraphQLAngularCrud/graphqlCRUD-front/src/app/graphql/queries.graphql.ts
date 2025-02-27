import gql from "graphql-tag";

const GET_BRANDS = gql`
  query GetAllBrands($page: Int!, $size: Int!) {
    getAllBrands(page: $page, size: $size) {
      content {
        country
        id
        name
      }
    }
  }
`;

const GET_MODELS = gql`
  query GetModelsByBrandId($brandId: ID!, $page: Int, $size: Int) {
    getModelsByBrandId(brandId: $brandId, page: $page, size: $size) {
      content {
        id
        name
        brandId
      }
    }
  }
`

export { GET_BRANDS, GET_MODELS };