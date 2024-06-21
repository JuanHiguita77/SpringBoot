export interface Content {
    content:          Company[];
    pageable:         Pageable;
    last:             boolean;
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    sort:             Sort;
    numberOfElements: number;
    first:            boolean;
    empty:            boolean;
}

export interface Company {
    id:       string;
    name:     string;
    location: string;
    contact:  string;
    vacants:  Vacant[];
}

export interface Vacant {
    companyId: any;
    id:          number;
    title:       string;
    description: string;
    status:      string;
}

export interface Pageable {
    pageNumber: number;
    pageSize:   number;
    sort:       Sort;
    offset:     number;
    paged:      boolean;
    unpaged:    boolean;
}

export interface Sort {
    unsorted: boolean;
    empty:    boolean;
    sorted:   boolean;
}

