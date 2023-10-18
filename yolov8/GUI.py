import tkinter as tk
from PIL import Image, ImageTk

class TwoImageDisplayGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Two Image Viewer")

        # image paths
        self.image_paths = ["image1.jpg", "image2.jpg"]

        #create two labels to show the photos
        self.image_label1 = tk.Label(root)
        self.image_label1.grid(row=0, column=0, padx=10, pady=10)

        self.image_label2 = tk.Label(root)
        self.image_label2.grid(row=0, column=1, padx=10, pady=10)

        # display the photos
        self.load_and_display_images()

    def load_and_display_images(self):
        for i, image_path in enumerate(self.image_paths):
            image = Image.open(image_path)
            image = image.resize((300, 300), Image.ANTIALIAS)
            photo = ImageTk.PhotoImage(image)

            if i == 0:
                self.image_label1.config(image=photo)
                self.image_label1.image = photo
            elif i == 1:
                self.image_label2.config(image=photo)
                self.image_label2.image = photo

if __name__ == "__main__":
    root = tk.Tk()
    app = TwoImageDisplayGUI(root)
    root.mainloop()
