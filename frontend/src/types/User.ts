export type User = {
    id: string,
    username: string,
    password: string,
    role: "ADMIN" | "USER",
    firstName: string,
    lastName: string,
    email: string,
    imageUrl: string
}