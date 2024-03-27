export type Purchase = {
    id: string,
    productId: string,
    productName: string,
    supplierId: string,
    supplierName: string,
    purchasePrice: number,
    quantity: number,
    totalPrice: number,
    purchaseDate: Date,
    createdAt: Date,
    updatedAt: Date
    active: boolean
}