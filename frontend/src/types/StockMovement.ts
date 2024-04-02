export type StockMovement = {
    id: string,
    productId: string,
    productName: string,
    quantity: number,
    type: string,
    reason: string,
    createdAt: Date
}