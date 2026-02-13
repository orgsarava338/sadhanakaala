interface ApiResponse<T> {
    data: T;
    errors: ApiError[];
    warnings: ApiWarning[];
}

interface ApiError {
    code: string;
    message: string;
}

interface ApiWarning {
    code: string;
    message: string;
}